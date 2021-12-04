import * as BT from "./productTypes";
import axios from "axios";

export const fetchProduct = () => {
  return (dispatch) => {
    dispatch({
      type: BT.FETCH_PRODUCT_REQUEST,
    });
    axios
      .get("http://localhost:8081/api/products")
      .then((response) => {
        dispatch(productSuccess(response.data));
      })
      .catch((error) => {
        dispatch(productFailure(error));
      });
  };
};

const productSuccess = (product) => {
  return {
    type: BT.PRODUCT_SUCCESS,
    payload: product,
  };
};

const productFailure = (error) => {
  return {
    type: BT.PRODUCT_FAILURE,
    payload: error,
  };
};