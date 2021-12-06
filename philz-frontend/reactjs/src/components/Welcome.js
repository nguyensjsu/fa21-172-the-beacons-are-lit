
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import productImage from '../productImage.json';

const Welcome = (props) => {
  const [products, ProductList] = useState("");

  useEffect(() => {
    if (products === "") {
      axios.get("http://localhost:8081/rest/products").then((response) => {
        ProductList(response.data);
      });
    }
  }, [products]);

  return (
    <>
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
                   <button hidden>Add to Cart</button>
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

  // return (
  //   <Card bg="dark" text="light">
  //     <Card.Header>Our Products</Card.Header>
  //     <Card.Body style={{ overflowY: "scroll", height: "570px" }}>
  //       {products &&
  //         products.map((p) => (
  //           <blockquote>
  //             <p>{p.name}</p>
  //             <p>{p.roast}</p>
              
  //           </blockquote>
  //         ))}
  //     </Card.Body>
  //   </Card>
  // );
};

export default Welcome;

