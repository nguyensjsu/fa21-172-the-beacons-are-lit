import React, { Component } from "react";
import axios from "axios";

import { connect } from "react-redux";
import { saveProduct } from "../../services/index";

import { Card, Form, Button, Col, InputGroup, Image } from "react-bootstrap";
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
    id: "",
    name: "",
    roast: "",
    price: "",
  };

  componentDidMount() {
    const productId = +this.props.match.params.id;
  }

  resetBook = () => {
    this.setState(() => this.initialState);
  };

  submitBook = (event) => {
    event.preventDefault();

    const product = {
      name: this.state.name,
      roast: this.state.roast,
      price: this.state.price,
    };

    this.props.saveProduct(product);
    setTimeout(() => {
      if (this.props.productObject.product != null) {
        //this.setState({ show: true, method: "post" });
        this.setState({ show: true });
        axios
          .post("http://localhost:8081/rest/api/{username}", {
            productL: [this.props.product],
          })
          .then((response) => {
            console.log("response is: ", response);
            this.props.history.push({
              pathname: "/list",
            });
          })
          .catch(function () {
            console.log("error");
          });
        setTimeout(() => this.setState({ show: false }), 3000);
      } else {
        this.setState({ show: false });
      }
    }, 2000);
    this.setState(this.initialState);
  };

  productChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  //   bookList = () => {
  //     return this.props.history.push("/list");
  //   };

  render() {
    const { name, roast, price } = this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={
              this.state.method === "put"
                ? "Cart Updated Successfully."
                : "Cart Saved Successfully."
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
            onSubmit={this.state.id ? this.updateBook : this.submitBook}
            id="bookFormId"
          >
            <Card.Body>
              <Form.Row>
                <Form.Group as={Col} controlId="formGridTitle">
                  <Form.Label>Coffee Name</Form.Label>
                  <Form.Control
                    type="text"
                    name="name"
                    value={name}
                    className={"bg-dark text-white"}
                    defaultValue={this.state.name}
                    enabled
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="formGridAuthor">
                  <Form.Label>Roast</Form.Label>
                  <Form.Control
                    type="text"
                    name="roast"
                    value={roast}
                    className={"bg-dark text-white"}
                    defaultValue={this.state.roast}
                    enabled
                  />
                </Form.Group>
              </Form.Row>
              <Form.Row>
                <Form.Group as={Col}>
                  <Form.Label>Price $</Form.Label>
                  <Form.Control
                    type="text"
                    name="price"
                    value={price}
                    defaultValue={this.state.price}
                    enabled
                  />
                </Form.Group>
              </Form.Row>
            </Card.Body>

            <Card.Footer style={{ textAlign: "right" }}>
              <Button size="m" variant="success" type="submit">Buy</Button>
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
    saveProduct: (product) => dispatch(saveProduct(product)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Product);
