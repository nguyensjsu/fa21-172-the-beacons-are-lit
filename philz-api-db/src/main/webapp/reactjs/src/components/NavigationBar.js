import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navbar, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
} from "@fortawesome/free-solid-svg-icons";
import { logoutUser } from "../services/index";

const NavigationBar = () => {
  const auth = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  const logout = () => {
    dispatch(logoutUser());
  };

  const guestLinks = (
    <>
      <div className="mr-auto"></div>
      <Nav className="navbar-right">
        <Link to={"register"} className="nav-link">
          <FontAwesomeIcon icon={faUserPlus} /> Register
        </Link>
        <Link to={"login"} className="nav-link">
          <FontAwesomeIcon icon={faSignInAlt} /> Login
        </Link>
      </Nav>
    </>
  );
  const userLinks = (
    <>
      <Nav className="mr-auto">
        <Link to={"users"} className="nav-link">
          User List
        </Link>
      </Nav>
      <Nav className="navbar-right">
        <Link to={"logout"} className="nav-link" onClick={logout}>
          <FontAwesomeIcon icon={faSignOutAlt} /> Logout
        </Link>
      </Nav>
    </>
  );

  return (
    <Navbar bg="dark" variant="dark">
      <Link to={auth.isLoggedIn ? "home" : ""} className="navbar-brand">
        <img
          src="./logo1.jpg"
          width="25"
          height="25"
          alt="brand"
        />{" "}
        Coffee Shop
      </Link>
      {auth.isLoggedIn ? userLinks : guestLinks}
    </Navbar>
  );
};

export default NavigationBar;
