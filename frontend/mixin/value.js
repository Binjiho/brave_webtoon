export default {
  methods: {
    /**
     * 날짜 포맷 변환
     * https://docs.oracle.com/javase/9/docs/api/java/text/SimpleDateFormat.html
     * @param string dateValue
     * @param string format
     * @returns string|null
     */
    formattedDate(dateValue, format = "YYYY-MM-dd") {
      if (dateValue) {
        const date = new Date(dateValue);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hour = date.getHours();
        const minutes = date.getMinutes();
        const seconds = date.getSeconds();
        const weekDay = date.getDay();
        const week = ["일", "월", "화", "수", "목", "금", "토"];

        return format
          .replace(/[Yy]{4}/gi, year) // YYYY:2022
          .replace(/[Yy]{2}/g, String(year).substring(2, 4)) // YY:22
          .replace(/M{2}/g, month > 9 ? month : "0" + month) // MM:01
          .replace(/M/g, month) // M: 1
          .replace(/d{2}/g, day > 9 ? day : "0" + day) // dd:01
          .replace(/d/g, day) // d: 1
          .replace(/H{2}/g, hour > 9 ? hour : "0" + hour) // HH: 13, 01
          .replace(/H/g, hour) // H: 13, 1
          .replace(
            /h{2}/g,
            hour == 12 ? 12 : hour % 12 > 9 ? hour % 12 : "0" + (hour % 12)
          ) // h:13 => 01
          .replace(/h/g, hour == 12 ? 12 : hour % 12) // h:13 => 1
          .replace(/m{2}/g, minutes > 9 ? minutes : "0" + minutes) // mm:01
          .replace(/m/g, minutes) // m:1
          .replace(/s{2}/g, seconds > 9 ? seconds : "0" + seconds) // ss:01
          .replace(/s/g, seconds) // s:1
          .replace(/E/g, week[weekDay]) // E:월
          .replace(/[Aa]{2}/g, hour >= 12 ? "오후" : "오전") /// AA:오전
          .replace(/A/g, hour >= 12 ? "PM" : "AM") // A:AM
          .replace(/a/g, hour >= 12 ? "pm" : "am"); // a:am
      } else {
        return null;
      }
    },
  },
};
