import httpRequest from "@/constants/httpRequest";

export default {
  methods: {
    async getAccessToken() {
      return true;
    },
    async sendPost(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      const accessToken = await this.getAccessToken();
      if (accessToken !== null) {
        this.requestPost(
          relativeUrl,
          requestData,
          successFunction,
          failureFunction,
          contentsType
        );
      }
    },
    async sendPut(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      const accessToken = await this.getAccessToken();
      if (accessToken !== null) {
        this.requestPut(
          relativeUrl,
          requestData,
          successFunction,
          failureFunction,
          contentsType
        );
      }
    },
    async sendGet(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      const accessToken = await this.getAccessToken();

      if (accessToken !== null) {
        this.requestGet(
          relativeUrl,
          requestData,
          successFunction,
          failureFunction,
          contentsType
        );
      }
    },
    async sendDelete(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      const accessToken = await this.getAccessToken();
      if (accessToken !== null) {
        this.requestDelete(
          relativeUrl,
          requestData,
          successFunction,
          failureFunction,
          contentsType
        );
      }
    },
    sendAnonymousPost(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.requestPost(
        relativeUrl,
        requestData,
        successFunction,
        failureFunction,
        contentsType
      );
    },
    sendAnonymousPut(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.requestPut(
        relativeUrl,
        requestData,
        successFunction,
        failureFunction,
        contentsType
      );
    },
    sendAnonymousGet(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.requestGet(
        relativeUrl,
        requestData,
        successFunction,
        failureFunction,
        contentsType
      );
    },
    sendAnonymousDelete(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.requestDelete(
        relativeUrl,
        requestData,
        successFunction,
        failureFunction,
        contentsType
      );
    },
    requestPost(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.$api
        .post(relativeUrl, requestData, {
          headers: {
            "Content-Type": contentsType,
          },
        })
        .then((response) => {
          if (successFunction) {
            successFunction(response);
          } else {
            this.reload();
          }
        })
        .catch((error) => {
          this.raiseError(
            this.getStatusCode(error),
            this.getStatusMessage(error)
          );
        });
    },
    requestPut(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.$api
        .put(relativeUrl, requestData, {
          headers: {
            "Content-Type": contentsType,
          },
        })
        .then((response) => {
          if (successFunction) {
            successFunction(response);
          } else {
            this.reload();
          }
        })
        .catch((error) => {
          this.raiseError(
            this.getStatusCode(error),
            this.getStatusMessage(error)
          );
        });
    },
    requestGet(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.$api
        .get(relativeUrl, {
          params: requestData,
          headers: {
            "Content-Type": contentsType,
          },
        })
        .then((response) => {
          if (successFunction) {
            successFunction(response);
          } else {
            this.reload();
          }
        })
        .catch((error) => {
          this.raiseError(
            this.getStatusCode(error),
            this.getStatusMessage(error)
          );
        });
    },
    requestDelete(
      relativeUrl,
      requestData,
      successFunction = null,
      failureFunction = null,
      contentsType = httpRequest.REQUEST_HEADER_CONTENTS_JSON
    ) {
      this.$api
        .delete(relativeUrl, {
          data: requestData,
          headers: {
            "Content-Type": contentsType,
          },
        })
        .then((response) => {
          if (successFunction) {
            successFunction(response);
          } else {
            this.reload();
          }
        })
        .catch((error) => {
          this.raiseError(
            this.getStatusCode(error),
            this.getStatusMessage(error)
          );
        });
    },
    raiseError(statusCode, message = null) {
      throw showError({
        statusCode: statusCode,
        message: message,
        fatal: true, // client에서 에러 페이지로 바로 이동한다.
      });
    },
    getStatusCode(error) {
      if (error.response) {
        // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
        // console.log(error.response.data);
        // console.log(error.response.status);
        // console.log(error.response.headers);
        return error.response.status;
      } else if (error.request) {
        // 요청이 이루어 졌으나 응답을 받지 못했습니다.
        // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
        // Node.js의 http.ClientRequest 인스턴스입니다.
        // console.log(error.request);
        return 503;
      } else {
        // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
        // console.log('Error', error.message);
        return 500;
      }
    },
    getStatusMessage(error) {
      if (error.response) {
        return error.response.statusText;
      } else if (error.request) {
        return error.message;
      } else {
        return "";
      }
    },
  },
};
