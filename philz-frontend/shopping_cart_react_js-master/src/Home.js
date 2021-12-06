
import { useEffect, useState } from 'react';
import './App.css';
import Menu from './Menu';
import {httpPost,httpPostwithToken} from './HttpConfig';
import {CartContextValue} from './ContextProvider';
import ProductItem from './productItem.json';
import { Link } from 'react-router-dom';

function Home() {
	// const [sign_in_up_model,setsignin_up_model] = useState('')
	// const[mobile,setMobile] = useState('');
	// const[password,setPassword] = useState('');
	// const[respassword,setRePassword] = useState('');
	const [productList,setProductList] = useState([]);
	const [cartData,dispatch] = CartContextValue()
	useEffect(()=>{
		//TODO check user login
		getCartApi()
	},[])

	const getCartApi = ()=>{		
		httpPostwithToken("addtocart/getCartsByUserId",{})
		.then((res)=>{				
			res.json() .then(data=>{
				if(res.ok){
					dispatch({
						"type":"add_cart",
						"data":data
					})
					//alert("Successfully added..")
				}else{
					alert(data.message)
				}
			})
		},error=>{
			alert(error.message);
		}
		)
	}
	const addCartApi=(productObj)=>{
		let obj = {
			"productId":productObj.id,			
			"qty":"1",
			"price":productObj.price
			
		}
		httpPostwithToken("addtocart/addProduct",obj)
		.then((res)=>{		
			res.json() .then(data=>{
				if(res.ok){
					dispatch({
						"type":"add_cart",
						"data":data
					})
					alert("Successfully added..")
				}else{
					alert(data.message)
				}
			})     
		}).catch(function(res){
			console.log("Error ",res);
			//alert(error.message);
		}
		)
	}

	const getProducts = ()=>{
		httpPostwithToken("product/getAll",{}).
		then((res)=>{
			res.json().then(response=>{
				if(res.ok){
					setProductList(response)
					
				}else{
					alert("Error in product api..")
				}
			})
			
			
		})
	}

  return (
    <div>
		<div className="banner-bottom">
			<div className="container">	
				<div className="col-md-7 wthree_banner_bottom_right">
					<div id="myTabContent" className="tab-content">
						<div role="tabpanel" className="tab-pane fade active in" id="home" aria-labelledby="home-tab">
							<div className="agile_ecommerce_tabs">
								{
									productList.map((product)=>(
										<div className="col-md-4 agile_ecommerce_tab_left">
											<div className="hs-wrapper">
												<img src={product.image}/>
												<div className="w3_hs_bottom">
													<ul>
														<li>
															<a href="#" data-toggle="modal" data-target="#myModal">
															<span className="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>
														</li>
													</ul>
												</div>
											</div> 
											<h5>
											<Link to={"/product/"+product.id}>{product.name}</Link>				
											</h5>
											<h5><a onClick={()=>addCartApi(product)} href="javascript:void(0)">Add Cart</a></h5>
											<div className="simpleCart_shelfItem">
												<p><i className="item_price">Rs.{product.price}</i></p>
											</div>
										</div>
										))
									}
								<div className="clearfix"> </div>
							</div>
						</div>
							
					</div>
				</div> 
			</div>
		</div>

	</div>
  );
}

export default Home;
