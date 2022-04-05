import './login.scss';
import { Button } from 'react-bootstrap'

import { useState } from 'react';
import baserequest from '../../core/baserequest';
import { Link, useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setemail] = useState("");
    const [password, setpassword] = useState("");
    const navigate = useNavigate();
    function login() {
        // var axios = require('axios');
        var data = JSON.stringify({
            "username": email,
            "password": password
        });
        // var data = new FormData();
        // data.append("email",email);
        // data.append("password",password);
        // var config = {
        // method: 'post',
        // url: 'http://localhost:8080/api/auth/signin',
        // headers: { 
        //     'Content-Type': 'application/json'
        // },
        // data : data
        // };
        baserequest.post('auth/signin', data)
            .then(function (response) {
                window.localStorage.setItem("token", response.data.token);
                navigate(`/`);
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <div className="App">
            <div className="container login">
                <div className="row">
                    <div className='col-md-6 login__image'>

                    </div>
                    <div className='col-md-6 login__right'>
                        <div className='login__right__title'>
                            Log In
                        </div>
                        <div className='login__right__subtitle'>
                            <div className='login__right__subtitle__left'>
                                New User?
                            </div>
                            <div className='login__right__subtitle__right'>
                                <Link to='/register'>Create an account</Link>
                            </div>
                        </div>
                        <div className='login__right__email'>
                            <div className='login__right__email__label'>Email</div>
                            <input value={email} onChange={e => setemail(e.target.value)} className='login__right__input' placeholder="John@example.com" />

                        </div>

                        <div className='login__right__password'>
                            <div className='login__right_password__label'>Password</div>
                            <p>
                                <input value={password} onChange={e => setpassword(e.target.value)} type='password' className='container_right__password__input' placeholder="********" />
                                <span className='login__right__password__vector'>
                                    <span className="bi bi-eye-slash-fill"><i className="bi bi-eye-slash"></i></span>
                                </span>
                            </p>

                        </div>



                        <button onClick={login} className='login__right__button'>
                            <span>Log In</span>
                        </button>


                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
