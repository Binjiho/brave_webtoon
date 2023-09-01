export default {
  methods: {
    urlParamsFormatter(filter = {}, pageData) {
      // page: 현재 페이지. itemsPerPage: 한 페이지당 크기
      const { page, itemsPerPage } = pageData;
      const params = { ...filter };
      if (page && itemsPerPage) {
        if (itemsPerPage > -1) {
          params.pageSize = itemsPerPage;

          params.offset =
            page - 1
              ? (page - 1) * itemsPerPage + 1
              : (page - 1) * itemsPerPage;
        }
      }
      return params;
    },
  },
};
