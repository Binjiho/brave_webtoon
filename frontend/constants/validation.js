const EMAIL_REGEX =
  /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const ID_REGEX = /^[a-z_|0-9]*$/;
//일반 전화 번호
const PHONE_REGEX = /^(\d{2,3})(\d{3,4})(\d{4})$/;
//핸드폰번호
const MOBILE_PHONE_REGEX = /^\d{3}\d{3,4}\d{4}$/;
//특수문자 체크
const SPECIAL_CHARACTER_REGEX = /[~!@#$%^&*()_+|<>?:{}-]/;
const PASSWORD_REGEX =
  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{10,}$/;
const DATE_REGEX = /[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])/;
const SERVICE_ID_MAX_LENGTH = 50;
const SERVICE_PW_MIN_LENGTH = 10;
const SERVICE_PW_MAX_LENGTH = 20;
const NUMBER_RESULT = /[^0-9]/g;

function isValidEmail(value) {
  return EMAIL_REGEX.test(value);
}

function isValidId(value) {
  let result = ID_REGEX.test(value);
  if (result) {
    result = 0 < value.length && value.length < SERVICE_ID_MAX_LENGTH;
  }
  return result;
}

function isValidPhoneNumber(value) {
  return PHONE_REGEX.test(value) || MOBILE_PHONE_REGEX.test(value);
}

function isValidPassword(value) {
  let result = PASSWORD_REGEX.test(value);

  if (result) {
    result =
      SERVICE_PW_MIN_LENGTH <= value.length &&
      value.length <= SERVICE_PW_MAX_LENGTH;
  }

  return result;
}

function isValidDate(value) {
  let result = DATE_REGEX.test(value);
  if (result) {
    result = value.length === 8;
  }
  return result;
}

function isSpecialCharacter(value) {
  //특수문자 있을시 false
  return !SPECIAL_CHARACTER_REGEX.test(value);
}

function returnOnlyNumbers(value) {
  return value.replace(NUMBER_RESULT, "");
}

function formatPhoneNumber(value) {
  return value.replace(PHONE_REGEX, `$1-$2-$3`);
}

export default {
  isValidEmail,
  isValidId,
  isValidPhoneNumber,
  isValidPassword,
  isValidDate,
  isSpecialCharacter,
  returnOnlyNumbers,
  formatPhoneNumber,
};
