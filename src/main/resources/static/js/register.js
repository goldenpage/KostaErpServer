function checkPassword() {
  const pw = document.querySelector('input[name="pw"]').value;
  const pwConfirm = document.querySelector('input[name="pwConfirm"]').value;
  if (pw !== pwConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }
  return true;
}

const sendPhoneCode = async () => {
  const res = await fetch(`/api/users/phone/code`, {
    method:"post",
    headers: {
      "Content-Type": "application/json",
    },
    body:JSON.stringify(phone)
  })
  const data  = await res.json();
}

const verifyPhoneCode = async  () => {
  const res = fetch(`/api/users/phone/verify`, {
    method: "post",
    headers:{
      "Content-Type": "application/json",
    },
    body:JSON.stringify(code)
  })

  const data = await res.json();
}

const checkBid = async  () => {
  const res = await fetch('/api/users/business/status',{
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(bid)
  })

  const data = await res.json();
}

const register = async () => {
  const res = await fetch(`/api/auth/register`, {
    method:"post",
    body: JSON.stringify(user)
  })

  const data = await res.json();
}

const registerBtn = document.querySelector("#registerBtn");
const sendBtn = document.querySelector("#sendBtn");
const verifyBtn = document.querySelector("#verifyBtn")

registerBtn.addEventListener("click",() => {
  register();

  if (register.status == 200) {
    location.href = "/login";
  }else{
    location.href = "/register";
  }

})

sendBtn.addEventListener("click", () => {
  sendPhoneCode()

  if (sendPhoneCode.status == 200) {
    phone.value = '';
  }else{
    phone.value = '';
  }
})

verifyBtn.addEventListener("click", ()=> {
  verifyPhoneCode()

  if (verifyPhoneCode.status == 200) {
    phone.value = '';
  }else{
    phone.value = '';
  }
})