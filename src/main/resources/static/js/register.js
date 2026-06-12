const registerForm = document.querySelector("#registerForm");
const registerBtn = document.querySelector("#registerBtn");
const sendBtn = document.querySelector("#sendBtn");
const verifyBtn = document.querySelector("#verifyBtn");
const phoneInput = document.querySelector("#phone");
const phoneCodeInput = document.querySelector("#phoneCode");
const phoneMessage = document.querySelector("#phoneMessage");
const businessMessage = document.querySelector("#businessMessage");
const documentInput = document.querySelector("#documentFile");

let phoneVerified = false;

const normalizeDigits = (value) => value.replace(/\D/g, "");

const setMessage = (element, message, success = false) => {
  element.textContent = message;
  element.classList.toggle("success-message", success);
  element.classList.toggle("error-message", !success);
};

const readJson = async (response) => {
  const contentType = response.headers.get("content-type") || "";
  if (!contentType.includes("application/json")) {
    throw new Error(`서버가 JSON이 아닌 응답을 반환했습니다. (${response.status})`);
  }
  return response.json();
};

const requestJson = async (url, payload) => {
  const response = await fetch(url, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(payload),
  });
  const data = await readJson(response);

  if (!response.ok || data.success === false) {
    throw new Error(data.message || "요청 처리에 실패했습니다.");
  }
  return data;
};


const sendPhoneCode = async () => {
  const phone = normalizeDigits(phoneInput.value);
  if (!/^010\d{8}$/.test(phone)) {
    setMessage(phoneMessage, "010으로 시작하는 휴대폰 번호 11자리를 입력해주세요.");
    return;
  }

  sendBtn.disabled = true;
  phoneVerified = false;
  try {
    const data = await requestJson("/api/auth/phone/code", {phone});
    phoneInput.value = phone;
    phoneCodeInput.value = "";
    setMessage(phoneMessage, data.message, true);
    phoneCodeInput.focus();
  } catch (error) {
    setMessage(phoneMessage, error.message);
  } finally {
    sendBtn.disabled = false;
  }
};

const verifyPhoneCode = async () => {
  const phone = normalizeDigits(phoneInput.value);
  const code = normalizeDigits(phoneCodeInput.value);

  if (!/^010\d{8}$/.test(phone)) {
    setMessage(phoneMessage, "휴대폰 번호를 확인해주세요.");
    return;
  }
  if (!/^\d{6}$/.test(code)) {
    setMessage(phoneMessage, "6자리 인증번호를 입력해주세요.");
    return;
  }

  verifyBtn.disabled = true;
  try {
    const data = await requestJson("/api/auth/phone/verify", {phone, code});
    phoneVerified = true;
    phoneInput.value = phone;
    phoneCodeInput.readOnly = true;
    sendBtn.disabled = true;
    setMessage(phoneMessage, data.message, true);
  } catch (error) {
    phoneVerified = false;
    setMessage(phoneMessage, error.message);
  } finally {
    verifyBtn.disabled = phoneVerified;
  }
};

const buildRegisterRequest = () => ({
  bId: registerForm.elements.bId.value.trim(),
  storeName: registerForm.elements.storeName.value.trim(),
  name: registerForm.elements.name.value.trim(),
  email: registerForm.elements.email.value.trim(),
  phone: normalizeDigits(phoneInput.value),
  pw: registerForm.elements.pw.value,
  storeType: registerForm.elements.storeType.value,
  storeCategory: registerForm.elements.storeCategory.value,
  marketingAgree: registerForm.elements.marketingAgree.checked,
});

const register = async () => {
  if (!registerForm.reportValidity()) {
    return;
  }

  const password = registerForm.elements.pw.value;
  const passwordConfirm = registerForm.elements.pwConfirm.value;
  if (password !== passwordConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    registerForm.elements.pwConfirm.focus();
    return;
  }
  if (!phoneVerified) {
    setMessage(phoneMessage, "휴대폰 인증을 먼저 완료해주세요.");
    phoneInput.focus();
    return;
  }

  const documentFile = documentInput.files[0];
  if (!documentFile) {
    setMessage(businessMessage, "사업자등록증 파일을 선택해주세요.");
    return;
  }

  const formData = new FormData();
  formData.append(
      "request",
      new Blob([JSON.stringify(buildRegisterRequest())], {type: "application/json"})
  );
  formData.append("document", documentFile);

  registerBtn.disabled = true;
  try {
    const response = await fetch("/api/auth/register", {
      method: "POST",
      body: formData,
    });
    const result = await readJson(response);

    if (!response.ok) {
      throw new Error(result.message || "회원가입 처리에 실패했습니다.");
    }
    if (result.status === "APPROVED") {
      alert(result.message);
      location.href = "/login";
      return;
    }
    if (result.status === "PENDING") {
      alert(result.message);
      location.href = "/login";
      return;
    }

    if (result.status === "REJECTED" || result.status === "RETRY") {
      throw new Error(result.message);
    }
    throw new Error("알 수 없는 회원가입 처리 상태입니다.");
  } catch (error) {
    setMessage(businessMessage, error.message);
  } finally {
    registerBtn.disabled = false;
  }
};

phoneInput.addEventListener("input", () => {
  const wasVerified = phoneVerified;
  phoneInput.value = normalizeDigits(phoneInput.value);
  phoneVerified = false;
  phoneCodeInput.readOnly = false;
  verifyBtn.disabled = false;
  sendBtn.disabled = false;
  if (wasVerified) {
    phoneCodeInput.value = "";
    setMessage(phoneMessage, "휴대폰 번호가 변경되어 인증이 취소되었습니다.");
  } else {
    phoneMessage.textContent = "";
  }
});

phoneCodeInput.addEventListener("input", () => {
  phoneCodeInput.value = normalizeDigits(phoneCodeInput.value);
});

sendBtn.addEventListener("click", sendPhoneCode);
verifyBtn.addEventListener("click", verifyPhoneCode);
registerForm.addEventListener("submit", (event) => {
  event.preventDefault();
  register();
});
