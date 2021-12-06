import React, { Component } from "react";
import axios from "axios";
import { connect } from "react-redux";
import {
  saveProduct,
} from "../../services/index";

import { Card, Form, Button, Col, InputGroup, Image, FormControl } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSave,
  faPlusSquare,
  faUndo,
  faList,
  faEdit,
} from "@fortawesome/free-solid-svg-icons";
import MyToast from "../MyToast";

class Product extends Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.state = {
      show: false,
    };
  }

  initialState = {
    productId: "",
    name: "",
    blendType: "",
    price: "",
    username:"",
  };

  componentDidMount() {
    const bookId = +this.props.match.params.id;
    // if (bookId) {
    //   this.findBookById(bookId);
    // }
    // this.findAllLanguages();
  }

  resetBook = () => {
    this.setState(() => this.initialState);
  };

  
  makePayment = (event) => {
    event.preventDefault();

    const product = {
      productId: this.state.productId,
      name: this.state.name,
      blendType: this.state.blendType,
      price: this.state.price,
    };

    
    axios.post("http://localhost:8081/rest/api/acart/${username}",{
      order:product,
      username: 'test',
      status:'IN_PROGRESS',
      total:'999',
    }).then((response) => {
      //ProductList(response.data);
      console.log("data", response.data);
    });
      
    
  
    //this.props.saveProduct(product);
    // setTimeout(() => {
    //   if (this.props.productObject.product != null) {
    //     this.setState({ show: true, method: "post" });
    //     setTimeout(() => this.setState({ show: false }), 3000);
    //   } else {
    //     this.setState({ show: false });
    //   }
    // }, 2000);
    this.setState(this.initialState);
  };


  bookChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  // bookList = () => {
  //   return this.props.history.push("/list");
  // };

  render() {
    const { productId, name, blendType, price, username } =
      this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={
              this.state.method === "put"
                ? "Book Updated Successfully."
                : "Book Saved Successfully."
            }
            type={"success"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <FontAwesomeIcon icon={this.state.id ? faEdit : faPlusSquare} />{" "}
            {this.state.id ? "Update Book" : "Add New Book"}
          </Card.Header>
          <Form
            onReset={this.resetBook}
            onSubmit={this.state.id ? this.updateBook : this.makePayment}
            id="bookFormId"
          >
            <Card.Body>
              <Form.Row>
                <Form.Group as={Col} controlId="formGridTitle">
                  <Form.Label>ProductId &nbsp;</Form.Label>
                  <select name="productId" value={productId} onChange={this.bookChange}>
                    <option value="1"> 1 - Jacob's Wonderbar</option>
                    <option value="2"> 2 - Aromatic Arabic</option>
                  </select>
                  {/* <Form.Control required
                    autoComplete="off"
                    type="test"
                    name="title"
                    value={title}
                    onChange={this.bookChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Book Title">
                  </Form.Control> */}
                </Form.Group>
                <Form.Group as={Col} controlId="formGridAuthor">
                  <Form.Label>Product Name &nbsp;</Form.Label>
                  <select name="name" value={name} onChange={this.bookChange}>
                    <option value="Jacob's Wonderbar">Jacob's Wonderbar</option>
                    <option value="Aromatic Arabic">Aromatic Arabic</option>
                  </select>
                  {/* <Form.Control
                    required
                    autoComplete="off"
                    type="test"
                    name="author"
                    value={author}
                    onChange={this.bookChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Book Author"
                  /> */}
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col} controlId="formGridISBNNumber">
                  <Form.Label>Blend Type &nbsp;</Form.Label>
                  <select name="blendType" value={blendType} onChange={this.bookChange}>
                    <option value="dark">Dark</option>
                    <option value="dark">Dark</option>
                  </select>
                  {/* <Form.Control
                    required
                    autoComplete="off"
                    type="test"
                    name="isbnNumber"
                    value={isbnNumber}
                    onChange={this.bookChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Book ISBN Number"
                  /> */}
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col} controlId="formGridPrice">
                  <Form.Label>Price &nbsp;</Form.Label>
                  <select name="price" value={price} onChange={this.bookChange}>
                    <option value="price">18.50</option>
                    <option value="price">18.50</option>
                  </select>
                  {/* <Form.Control
                    required
                    autoComplete="off"
                    type="test"
                    name="price"
                    value={price}
                    onChange={this.bookChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Book Price"
                  /> */}
                </Form.Group>
                
              </Form.Row>
            </Card.Body>
            <Card.Footer style={{ textAlign: "right" }}>
              <Button size="sm" variant="success" type="submit">
                <FontAwesomeIcon icon={faSave} />{" "}
                {this.state.id ? "Update" : "Save"}
              </Button>{" "}
              {/* <Button size="sm" variant="info" type="reset">
                <FontAwesomeIcon icon={faUndo} /> Reset
              </Button>{" "} */}
              {/* <Button
                size="sm"
                variant="info"
                type="button"
                onClick={() => this.bookList()}
              >
                <FontAwesomeIcon icon={faList} /> Book List
              </Button> */}
            </Card.Footer>
          </Form>
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
    //saveProduct: (product) => dispatch(saveProduct(product)),
    // fetchBook: (bookId) => dispatch(fetchBook(bookId)),
    // updateBook: (book) => dispatch(updateBook(book)),
    // fetchLanguages: () => dispatch(fetchLanguages()),
    // fetchGenres: () => dispatch(fetchGenres()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Product);
