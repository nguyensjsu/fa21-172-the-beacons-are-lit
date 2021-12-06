import * as BT from "./productTypes";
import axios from "axios";

// export const saveProduct = (product) => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.SAVE_PRODUCT_REQUEST,
//     });
//     axios
//       .post("http://localhost:8081/rest/api/acart/{username}", product)
//       .then((response) => {
//         dispatch(productSuccess(response.data));
//       })
//       .catch((error) => {
//         dispatch(productFailure(error));
//       });
//   };
// };

// export const fetchBook = (productId) => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.FETCH_BOOK_REQUEST,
//     });
//     axios
//       .get("http://localhost:8081/rest/books/" + bookId)
//       .then((response) => {
//         dispatch(bookSuccess(response.data));
//       })
//       .catch((error) => {
//         dispatch(bookFailure(error));
//       });
//   };
// };

// export const updateBook = (book) => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.UPDATE_BOOK_REQUEST,
//     });
//     axios
//       .put("http://localhost:8081/rest/books", book)
//       .then((response) => {
//         dispatch(bookSuccess(response.data));
//       })
//       .catch((error) => {
//         dispatch(bookFailure(error));
//       });
//   };
// };

// export const deleteBook = (bookId) => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.DELETE_BOOK_REQUEST,
//     });
//     axios
//       .delete("http://localhost:8081/rest/books/" + bookId)
//       .then((response) => {
//         dispatch(bookSuccess(response.data));
//       })
//       .catch((error) => {
//         dispatch(bookFailure(error));
//       });
//   };
// };

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

// export const fetchLanguages = () => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.FETCH_LANGUAGES_REQUEST,
//     });
//     axios
//       .get("http://localhost:8081/rest/books/languages")
//       .then((response) => {
//         dispatch({
//           type: BT.LANGUAGES_SUCCESS,
//           payload: response.data,
//         });
//       })
//       .catch((error) => {
//         dispatch({
//           type: BT.LANGUAGES_FAILURE,
//           payload: error,
//         });
//       });
//   };
// };

// export const fetchGenres = () => {
//   return (dispatch) => {
//     dispatch({
//       type: BT.FETCH_GENRES_REQUEST,
//     });
//     axios
//       .get("http://localhost:8081/rest/books/genres")
//       .then((response) => {
//         dispatch({
//           type: BT.GENRES_SUCCESS,
//           payload: response.data,
//         });
//       })
//       .catch((error) => {
//         dispatch({
//           type: BT.GENRES_FAILURE,
//           payload: error,
//         });
//       });
//   };
// };
