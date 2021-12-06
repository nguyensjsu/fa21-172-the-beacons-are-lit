
import { useEffect, useState } from 'react';
import './App.css';
import Menu from './Menu';
import {httpPost,httpPostwithToken} from './HttpConfig';
import ProductItem from './productItem.json';

export default function Product() {
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
				<button type="submit" class="w3ls-cart">Add to cart</button>
                </p>
                </div>

              </div>

            </div>

          );
        })}
      </div>
    </div>
    )
}