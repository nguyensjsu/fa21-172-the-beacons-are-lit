import * as BT from "./productTypes";

const initialState = {
  product: "",
  error: "",
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case BT.FETCH_PRODUCT_REQUEST:
      return {
        ...state,
      };
    case BT.PRODUCT_SUCCESS:
      return {
        product: action.payload,
        error: "",
      };
    case BT.PRODUCT_FAILURE:
      return {
        product: "",
        error: action.payload,
      };
    default:
      return state;
  }
};

export default reducer;
