import React, {Component, useEffect, useState} from "react";
import axios from "axios";
import Select from 'react-select';
import 'bootstrap/dist/css/bootstrap.min.css';

 import {connect, useSelector, useDispatch} from "react-redux";
// import { saveProduct } from "../../services/index";
// import authToken from "../utils/authToken";
 import {Row, Card, Form, Button, Col, FormControl, Alert, InputGroup, Image, DropdownButton} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignInAlt,
  faEnvelope,
  faLock,
  faSave,
  faPlusSquare,
  faUndo,
  faList,
  faEdit,
} from "@fortawesome/free-solid-svg-icons";
// import MyToast from "../MyToast";

import { authenticateUser,saveProduct } from "../../services/index";

// const Product = (props) => {
//
//   const initialState = {
//     firstname: "",
//     lastname: "",
//     address: "",
//     city: "", state:"", zip:"", phone:"", cardnum:"", cardexpmon:"", cardexpyear:"", cardcvv:"",
//     email:"",
//   };
//
//   const [product, setProduct] = useState(initialState);
//
//   const credentialChange = (event) => {
//     const { name, value } = event.target;
//     setProduct({ ...product, [name]: value });
//   };
//
//   const dispatch = useDispatch();
//
//   const submitBook = (event) => {
//     event.preventDefault();
//
//     const book = {
//       title: this.state.title,
//       author: this.state.author,
//       coverPhotoURL: this.state.coverPhotoURL,
//       isbnNumber: this.state.isbnNumber,
//       price: this.state.price,
//       language: this.state.language,
//       genre: this.state.genre,
//     };
//
//     this.props.saveProduct(book);
//     setTimeout(() => {
//       if (this.props.productObject.book != null) {
//         this.setState({ show: true, method: "post" });
//         //setTimeout(() => this.setState({ show: false }), 3000);
//       } else {
//         //this.setState({ show: false });
//       }
//     }, 2000);
//     this.setState(this.initialState);
//   };
//
//   const validateOrder = () => {
//     dispatch(saveProduct(product))
//         .then((response) => {
//           console.log(response.data);
//           return props.history.push("/home");
//         })
//         .catch((error) => {
//           console.log(error.message);
//         });
//   };
//
//   const state={
//     selectOptions:[],
//     productID:"",
//     blendType:"",
//     name:"",
//     price:"",
//
//   }
//   //get all 18 products info for drop down button
//   const [products, ProductList] = useState("");
//   useEffect(() => {
//     if (products === "") {
//       axios.get("http://localhost:8081/rest/products").then((response) => {
//         //ProductList(response.data);
//       });
//     }
//   }, [products]);
//   console.log(products);
//
//   const getOptions=()=>{
//     let options = products.map(d => ({
//       "productID": d.productID,
//       "blendType": d.blendType,
//       "name": d.name,
//       "price": d.price
//     }))
//
//     this.setState({selectOptions:options})
//   }
//
//   const handleChange=(e)=>{
//     this.setState({productID:e.productID,blendType:e.blendType,name:e.name,price:e.price})
//   }
//
//   const componentDidMount=()=>{
//     getOptions()
//   }
//
//
//   return (
//       <div>
//         <div className="container">
//           <div className="row"></div>
//           <div className="col-md-3"></div>
//           <div className="col-md-6">
//             <Select  options={state.selectOptions} onChange={handleChange}/>
//           </div>
//           <div className="col-md-4"></div>
//           <p>
//             <div>You have selected product id <strong>{state.productID}</strong></div>
//             <div>Product Name: <strong>{state.name}</strong></div>
//             <div>Product Roast Type: <strong>{state.blendType}</strong></div>
//             <div>Product Price: <strong>{state.price}</strong></div>
//           </p>
//         </div>
//
//       <Row className="justify-content-md-center">
//         <Col xs={5}>
//           <Card className={"border border-dark bg-dark text-white"}>
//             <Card.Body>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faEnvelope} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="firstname"
//                         value={product.firstname}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter First Name"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="lastname"
//                         value={product.lastname}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Last Name"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="address"
//                         value={product.address}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Address"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="city"
//                         value={product.city}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter City"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="state"
//                         value={product.state}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter State"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="zip"
//                         value={product.zip}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Zipcode"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="phone"
//                         value={product.phone}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Phone Number"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="cardnum"
//                         value={product.cardnum}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Card Number"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="cardexpmon"
//                         value={product.cardexpmon}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Card Expired Month"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="cardexpyear"
//                         value={product.cardexpyear}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Card Expired Year"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="cardcvv"
//                         value={product.cardcvv}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Card CVV"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <InputGroup>
//                     <InputGroup.Prepend>
//                       <InputGroup.Text>
//                         <FontAwesomeIcon icon={faLock} />
//                       </InputGroup.Text>
//                     </InputGroup.Prepend>
//                     <FormControl
//                         required
//                         autoComplete="off"
//                         type="text"
//                         name="email"
//                         value={product.email}
//                         onChange={credentialChange}
//                         className={"bg-dark text-white"}
//                         placeholder="Enter Email"
//                     />
//                   </InputGroup>
//                 </Form.Group>
//               </Form.Row>
//             </Card.Body>
//             <Card.Footer style={{ textAlign: "right" }}>
//               <Button
//                   size="sm"
//                   type="button"
//                   variant="success"
//                   onClick={validateOrder}
//                   disabled={product.email.length === 0 || product.firstname.length === 0}
//               >
//                 <FontAwesomeIcon icon={faSignInAlt} /> Check Out
//               </Button>{" "}
//               {/*<Button*/}
//               {/*    size="sm"*/}
//               {/*    type="button"*/}
//               {/*    variant="info"*/}
//               {/*    onClick={resetLoginForm}*/}
//               {/*    disabled={user.email.length === 0 && user.password.length === 0}*/}
//               {/*>*/}
//               {/*  <FontAwesomeIcon icon={faUndo} /> Reset*/}
//               {/*</Button>*/}
//             </Card.Footer>
//           </Card>
//         </Col>
//       </Row>
//       </div>
//   );
// };
//export default Product;

//Trial 2
export default class Product extends Component{
  constructor(props) {
    super(props);
    this.state={
      selectOptions:[],
      productID:"",
      blendType:"",
      name:"",
      price:""

    }
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    const userdata = new FormData(event.target);
    console.log(userdata);

    const json = {};
    Array.from(userdata.entries()).forEach(([key, value]) => {
      json[key] = value;
    })



    //post("http://localhost:8081/apple/api/payment", product)
    axios.post('http://localhost:8081/apple/api/payment', JSON.stringify(json),{
      headers:{
        'Content-type': 'application/json;charset=utf-8'
      }
    })

        .then(response => {

          console.log(response)

        });

    // axios({
    //   method: "post",
    //   url: "http://localhost:8081/apple/api/payment",
    //   data: userdata,
    // })
    //     .then(function (response) {
    //       //handle success
    //       console.log(response);
    //     })
    //     .catch(function (response) {
    //       //handle error
    //       console.log(response);
    //     });

    // axios('http://localhost:8081/api/payment', {
    //   method: 'POST',
    //   body: userdata,
    // });
  }

  async getOptions(){
    const res=await axios.get("http://localhost:8081/rest/products")
    const data = res.data

    const options = data.map(d=>({
      "productID":d.productID,
      "blendType":d.blendType,
      "name":d.name,
      "price":d.price
    }))

    this.setState(({selectOptions:options}))
  }

  handleChange(e){
    this.setState({productID:e.productID,blendType:e.blendType,name:e.name,price:e.price})
  }

  componentDidMount(){
    this.getOptions()
  }

  render() {
    console.log(this.state.selectOptions)
    return (
        <>
          <div className="container">
            <div className="row"></div>
            <div className="col-md-3"></div>
            <div className="col-md-6">
              <Select  options={this.state.selectOptions} onChange={this.handleChange.bind(this)}/>
            </div>
            <div className="col-md-4"></div>
            <p>
              <div>You have selected product id <strong>{this.state.productID}</strong></div>
              <div>Product Name: <strong>{this.state.name}</strong></div>
              <div>Product Roast Type: <strong>{this.state.blendType}</strong></div>
              <div>Product Price: <strong>{this.state.price}</strong></div>
            </p>
          </div>
          <form onSubmit={this.handleSubmit}>
            <br/>
            <label htmlFor="firstname">Enter First Name</label>
            <input id="firstname" name="firstname" type="text" required/>
            <br/>
            <label htmlFor="lastname">Enter Last Name</label>
            <input id="lastname" name="lastname" type="text" required/>
            <br/>
            <label htmlFor="address">Enter your address</label>
            <input id="address" name="address" type="text" required/>
            <br/>
            <label htmlFor="city">Enter your City</label>
            <input id="city" name="city" type="text" required/>
            <br/>
            <label htmlFor="state">Enter your state</label>
            <input id="state" name="state" type="text" required/>
            <br/>
            <label htmlFor="zip">Enter your zipcode</label>
            <input id="zip" name="zip" type="text" required/>
            <br/>
            <label htmlFor="phone">Enter your phone number</label>
            <input id="phone" name="phone" type="text" required/>
            <br/>
            <label htmlFor="cardnum">Enter your credit card number</label>
            <input id="cardnum" name="cardnum" type="text" required/>
            <br/>
            <label htmlFor="cardexpmon">Enter your card expired month</label>
            <input id="cardexpmon" name="cardexpmon" type="text" required/>
            <br/>
            <label htmlFor="cardexpyear">Enter your card expired year</label>
            <input id="cardexpyear" name="cardexpyear" type="text" required/>
            <br/>
            <label htmlFor="cardcvv">Enter your card CVV</label>
            <input id="cardcvv" name="cardcvv" type="text" required/>
            <br/>
            <label htmlFor="email">Enter your email</label>
            <input id="email" name="email" type="text" required/>
            <br/>
            <button>Send data!</button>
          </form>
        </>

    )
  }
}

// Trial 1

// class Product extends Component {
//   constructor(props) {
//     super(props);
//     this.state = this.initialState;
//     this.state = {
//       show: false,
//     };
//   }
//
//   initialState = {
//     id: "",
//     name: "",
//     roast: "",
//     price: "",
//   };
//
//   componentDidMount() {
//     const productId = +this.props.match.params.id;
//   }
//
//   resetBook = () => {
//     this.setState(() => this.initialState);
//   };
//
//   submitBook = (event) => {
//     event.preventDefault();
//
//     const product = {
//       name: this.state.name,
//       roast: this.state.roast,
//       price: this.state.price,
//     };
//
//     axios.post("http://localhost:8081/rest")
//
//     // this.props.saveProduct(product);
//     // setTimeout(() => {
//     //   if (this.props.productObject.product != null) {
//     //     //this.setState({ show: true, method: "post" });
//     //     this.setState({ show: true });
//     //     axios
//     //       .post("http://localhost:8081/rest/api/{username}", {
//     //         productL: [this.props.product],
//     //       })
//     //       .then((response) => {
//     //         console.log("response is: ", response);
//     //         this.props.history.push({
//     //           pathname: "/list",
//     //         });
//     //       })
//     //       .catch(function () {
//     //         console.log("error");
//     //       });
//     //     setTimeout(() => this.setState({ show: false }), 3000);
//     //   } else {
//     //     this.setState({ show: false });
//     //   }
//     // }, 2000);
//     this.setState(this.initialState);
//   };
//
//   productChange = (event) => {
//     this.setState({
//       [event.target.name]: event.target.value,
//     });
//   };
//
//   //   bookList = () => {
//   //     return this.props.history.push("/list");
//   //   };
//
//   render() {
//     const { name, roast, price } = this.state;
//
//     return (
//       <div>
//         <div style={{ display: this.state.show ? "block" : "none" }}>
//           <MyToast
//             show={this.state.show}
//             message={
//               this.state.method === "put"
//                 ? "Cart Updated Successfully."
//                 : "Cart Saved Successfully."
//             }
//             type={"success"}
//           />
//         </div>
//
//         <Card className={"border border-dark bg-dark text-white"}>
//           <Card.Header>
//             <FontAwesomeIcon icon={this.state.id ? faEdit : faPlusSquare} />{" "}
//             {this.state.id ? "Update Book" : "Add New Book"}
//           </Card.Header>
//           <Form
//             onReset={this.resetBook}
//             onSubmit={this.state.id ? this.updateBook : this.submitBook}
//             id="bookFormId"
//           >
//             <Card.Body>
//               <Form.Row>
//                 <Form.Group as={Col} controlId="formGridTitle">
//                   <Form.Label>Coffee Name</Form.Label>
//                   <Form.Control
//                     type="text"
//                     name="name"
//                     value={name}
//                     className={"bg-dark text-white"}
//                     defaultValue={this.state.name}
//                     enabled
//                   />
//                 </Form.Group>
//                 <Form.Group as={Col} controlId="formGridAuthor">
//                   <Form.Label>Roast</Form.Label>
//                   <Form.Control
//                     type="text"
//                     name="roast"
//                     value={roast}
//                     className={"bg-dark text-white"}
//                     defaultValue={this.state.roast}
//                     enabled
//                   />
//                 </Form.Group>
//               </Form.Row>
//               <Form.Row>
//                 <Form.Group as={Col}>
//                   <Form.Label>Price $</Form.Label>
//                   <Form.Control
//                     type="text"
//                     name="price"
//                     value={price}
//                     defaultValue={this.state.price}
//                     enabled
//                   />
//                 </Form.Group>
//               </Form.Row>
//             </Card.Body>
//
//             <Card.Footer style={{ textAlign: "right" }}>
//               <Button size="m" variant="success" type="submit">Buy</Button>
//             </Card.Footer>
//           </Form>
//         </Card>
//       </div>
//     );
//   }
// }
//
// const mapStateToProps = (state) => {
//   return {
//     productObject: state.product,
//   };
// };
//
// const mapDispatchToProps = (dispatch) => {
//   return {
//     saveProduct: (product) => dispatch(saveProduct(product)),
//   };
// };
//
// export default connect(mapStateToProps, mapDispatchToProps)(Product);
