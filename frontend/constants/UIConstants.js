import validation from "@/constants/validation";

const inputRule = [(v) => !!v || "필수로 입력해야합니다."];

const inputRules = {
  input: [...inputRule],
  select: [(v) => !!v || "필수로 선택해야 합니다."],
  id: [
    ...inputRule,
    (v) =>
      validation.isValidId(v) ||
      "아이디는 대문자 및 특수문자 제외 50자 이내로 작성 가능합니다",
  ],
  password: [
    ...inputRule,
    (v) =>
      validation.isValidPassword(v) ||
      "영문, 숫자, 특수문자 포함 10자 이상 20자 이하",
  ],
  email: [
    ...inputRule,
    (v) => validation.isValidEmail(v) || "유효한 이메일이 아닙니다.",
  ],
  phone: [
    ...inputRule,
    (v) =>
      validation.isSpecialCharacter(v) || "전화번호는 숫자만 입력가능합니다.",
    (v) => validation.isValidPhoneNumber(v) || "전화번호가 유효하지 않습니다.",
  ],
  phoneFormat: [
    ...inputRule,
    (v) =>
      validation.isValidPhoneNumber(validation.returnOnlyNumbers(v)) ||
      "전화번호가 유효하지 않습니다.",
  ],
};

export default {
  inputRules: inputRules,
};
