import axios from "axios";
import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import ProductItem from '../productItem.json';

const Welcome = (props) => {
  return (
    <div className="container">
      <div className="column columns is-multiline">
        {ProductItem.map(product => {
          return (
            <div className=" column is-half">
              <div className="box">
                <div key={product.id}>
                <img src={product.image}/>
                <p>{ product.name }</p>
                <p>{ product.description }</p>
                
                <p>${ product.price }</p>
                <p>
                  <button>Add to Cart</button>
                </p>
                </div>

              </div>

            </div>
            
          );
        })}
      </div>
    </div>
    
  );
};

export default Welcome;