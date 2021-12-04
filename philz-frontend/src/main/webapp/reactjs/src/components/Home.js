import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import authToken from "../utils/authToken";
import { Alert } from "react-bootstrap";
import productImage from '../productImage.json';
import axios from "axios";


const Home = () => {
  if (localStorage.jwtToken) {
    authToken(localStorage.jwtToken);
  }

  const auth = useSelector((state) => state.auth);

  const [products, ProductList] = useState("");

  useEffect(() => {
    if (products === "") {
      axios.get("http://localhost:8081/api/products").then((response) => {
        ProductList(response.data);
      });
    }
  }, [products]);

  return (
    <>
    <Alert style={{ backgroundColor: "#343A40", color: "#ffffff80" }}>
      Welcome {auth.username}
    </Alert>
        <div className="hero is-primary" text="light">
          <div className="hero-body container">
            <h4 className="title">Our Products</h4>
          </div>
        </div>
        <br />
        <div className="container"  >
          <div className="column columns is-multiline">
            {products &&
              products.map((p) => (
                <div className=" column is-half">
                  <div className="box">
                    <div className="media">
                      <div className="media-left">
                        {productImage.map(i => {
                          return( <img src={i.image}/>);
                        })}
                      </div>
                      <div className="media-content">
                      <b style={{ textTransform: "capitalize" }}>Coffee Name: {p.name}</b>
                      <div>Blend: {p.roast}</div>
                      <p>
                       <button>Add to Cart</button>
                      </p>
                      </div>
                    </div>
                    
                  </div>
                </div>
                
              ))}
          </div>
        </div>
      </>
  );
};

export default Home;
