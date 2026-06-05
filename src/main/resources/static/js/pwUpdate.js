let pwPhoneVerified = false;

function sendPwPhoneCode() {
  const bId = document.querySelector("#bId").value.trim();
  const name = document.querySelector("#name").value.trim();
  const phone = document.querySelector("#phone").value.trim();

  pwPhoneVerified = false;

  if (!bId || !name || !phone) {
    alert("아이디, 이름, 휴대폰 번호를 모두 입력해주세요.");
    return;
  }

  const xhr = new XMLHttpRequest();
  const url = '${pageContext.request.contextPath}/controller?cmd=pwPhoneSendAction';
  const params = 'bId=' + encodeURIComponent(bId)
      + '&name=' + encodeURIComponent(name)
      + '&phone=' + encodeURIComponent(phone);

  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.querySelector("#phoneMessage").innerText = xhr.responseText;
      } else {
        alert("인증번호 발송 중 오류가 발생했습니다.");
      }
    }
  };

  xhr.send(params);
}

function verifyPwPhoneCode() {
  const phone = document.querySelector("#phone").value.trim();
  const phoneCode = document.querySelector("#phoneCode").value.trim();

  if (!phoneCode) {
    alert("인증번호를 입력해주세요.");
    return;
  }

  const xhr = new XMLHttpRequest();
  const url = '${pageContext.request.contextPath}/controller?cmd=pwPhoneVerifyAction';
  const params = 'phone=' + encodeURIComponent(phone)
      + '&phoneCode=' + encodeURIComponent(phoneCode);

  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.querySelector("#phoneMessage").innerText = xhr.responseText;
        pwPhoneVerified = xhr.responseText.indexOf("완료") > -1;
      } else {
        alert("인증번호 확인 중 오류가 발생했습니다.");
      }
    }
  };

  xhr.send(params);
}

function checkPw() {
  const pw = document.querySelector("#pw").value;
  const pwConfirm = document.querySelector("#pwConfirm").value;

  if (!pwPhoneVerified) {
    alert("휴대폰 인증을 먼저 완료해주세요.");
    return false;
  }

  if (pw !== pwConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  return true;
}

document.querySelector("#bId").addEventListener("input", function () {
  pwPhoneVerified = false;
});
document.querySelector("#name").addEventListener("input", function () {
  pwPhoneVerified = false;
});
document.querySelector("#phone").addEventListener("input", function () {
  pwPhoneVerified = false;
});