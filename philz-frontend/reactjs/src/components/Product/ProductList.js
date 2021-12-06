import React, { Component } from "react";

import { connect } from "react-redux";
import { deleteBook } from "../../services/index";

import "./../../assets/css/Style.css";
import {
  Card,
  Table,
  Image,
  ButtonGroup,
  Button,
  InputGroup,
  FormControl,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
  faEdit,
  faTrash,
  faStepBackward,
  faFastBackward,
  faStepForward,
  faFastForward,
  faSearch,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import MyToast from "../MyToast";
import axios from "axios";


class ProductList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      order: [],
    };
  }

  componentDidMount() {
    this.findAllProducts(this.state.currentPage);
  }

  findAllProducts() {
    axios
      .get(
        "http://localhost:8081/rest/api/{username}" 
      )
      .then((response) => response.data)
      .then((data) => {
        this.setState({
          orders: data.content,
        });
      })
      .catch((error) => {
        console.log(error);
        localStorage.removeItem("jwtToken");
        this.props.history.push("/");
      });
  }

  // deleteBook = (bookId) => {
  //   this.props.deleteBook(bookId);
  //   setTimeout(() => {
  //     if (this.props.bookObject != null) {
  //       this.setState({ show: true });
  //       setTimeout(() => this.setState({ show: false }), 3000);
  //       this.findAllBooks(this.state.currentPage);
  //     } else {
  //       this.setState({ show: false });
  //     }
  //   }, 1000);
  // };

  

  searchChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };



  render() {
    const { orders } = this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={"Book Deleted Successfully."}
            type={"danger"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <div style={{ float: "left" }}>
              <FontAwesomeIcon icon={faList} /> Book List
            </div>
          
          </Card.Header>
          <Card.Body>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Roast</th>
                  <th>Price</th>
                </tr>
              </thead>
              <tbody>
                {orders.length === 0 ? (
                  <tr align="center">
                    <td colSpan="7">No Books Available.</td>
                  </tr>
                ) : (
                  orders.map((product) => (
                    <tr key={product.id}>
                      <td>
                      
                        {product.name}
                      </td>
                      <td>{product.roast}</td>
                      <td>{product.price}</td>
                      <td>
                        <ButtonGroup>
                          <Link
                            to={"edit/" + product.id}
                            className="btn btn-sm btn-outline-primary"
                          >
                            <FontAwesomeIcon icon={faEdit} />
                          </Link>{" "}
                          
                        </ButtonGroup>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </Table>
          </Card.Body>
          
        </Card>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    productObject: state.product,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    //deleteBook: (productId) => dispatch(deleteBook(bookId)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ProductList);
