function checkPassword() {
  const pw = document.querySelector('input[name="pw"]').value;
  const pwConfirm = document.querySelector('input[name="pwConfirm"]').value;
  if (pw !== pwConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }
  return true;
}

function sendPhoneCode() {
  const phone = document.querySelector("#phone").value
  if (!phone) {
    alert("휴대폰 번호를 입력해주세요")
    return;
  }
  const xhr = new XMLHttpRequest();
  const url = '${pageContext.request.contextPath}/controller?cmd=phoneSendAction&phone='
      + encodeURIComponent(phone);

  xhr.open('GET', url, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.querySelector("#phoneMessage").innerText = xhr.responseText;
      } else {
        alert("인증번호 발송 중 오류가 발생했습니다.");
      }
    }
  };

  xhr.send();

}

function sendPhoneCodeJquery() {
  const phone = document.querySelector("#phone").value

  if (!phone) {
    alert("휴대폰 번호를 입력해주세요")
    return;
  }
  const url = '${pageContext.request.contextPath}/controller?cmd=phoneSendAction&phone='
      + encodeURIComponent(phone);

  $.ajax({
    method: 'GET',
    url: url,
    success: function (result) {
      $("#phoneMessage").html(result);
    }
  });
}

function verifyPhoneCode() {
  const
      phone = document.querySelector("#phone").value;
  const
      phoneCode = document.querySelector("#phoneCode").value;

  const xhr = new XMLHttpRequest();
  const url = '${pageContext.request.contextPath}/controller?cmd=phoneVerifyAction&phone='
      + encodeURIComponent(phone)
      + '&phoneCode='
      + encodeURIComponent(phoneCode);

  xhr.open("GET", url, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.querySelector("#phoneMessage").innerText = xhr.responseText;
      } else {
        alert("인증번호 확인 중 오류가 발생했습니다.");
      }
    }
  };

  xhr.send();
}

function checkBid() {
  const bId = document.querySelector("#bId").value;

  if (!bId || bId.length !== 10) {
    alert("사업자번호 10자리를 입력해주세요.");
    return;
  }

  const xhr = new XMLHttpRequest();
  const url = '${pageContext.request.contextPath}/controller?cmd=idCheckAction&bId='
      + encodeURIComponent(bId);

  xhr.open("GET", url, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.querySelector("#businessMessage").innerText = xhr.responseText;
      } else {
        alert("사업자번호 인증 중 오류가 발생했습니다.");
      }
    }
  };

  xhr.send();
}