import './register.scss';
import { useState } from 'react';
import baserequest from '../../core/baserequest';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setemail] = useState("");
    const [password, setpassword] = useState("");

    const [username, setusername] = useState("");
    const navigate = useNavigate();
    function login() {
        // var axios = require('axios');
        var data = JSON.stringify({
            "username": username,
            "email": email,
            "password": password
        });

        // var config = {
        // method: 'post',
        // url: 'http://localhost:8080/api/auth/signin',
        // headers: { 
        //     'Content-Type': 'application/json'
        // },
        // data : data
        // };
        baserequest.post('auth/signup', data)
            .then(function (response) {
                // window.localStorage.setItem("token", response.data.token);
                navigate(`/login`);
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <div className="App">
            <div className="container register">
                <div className='container__image'>

                </div>
                <div className='container_right'>
                    <div className='container_right__title'>
                        SignUp
                    </div>
                    
                    <div className='container_right__username'>
                        <div className='container_right__username__label'>UserName</div>
                        <input value={username} onChange={e => setusername(e.target.value)} className='container_right__username__input' placeholder="John@example.com" />

                    </div>
                    <div className='container_right__email'>
                        <div className='container_right__email__label'>Email</div>
                        <input value={email} onChange={e => setemail(e.target.value)} className='container_right__email__input' placeholder="John@example.com" />

                    </div>

                    <div className='container_right__password'>
                        <div className='container_right_password__label'>Password</div>
                        <p>
                            <input value={password} onChange={e => setpassword(e.target.value)} type='password' className='container_right__password__input' placeholder="********" />
                            <span className='container_right__password__vector'>
                                <span className="bi bi-eye-slash-fill"><i className="bi bi-eye-slash"></i></span>
                            </span>
                        </p>

                    </div>

                    
                    <button onClick={login} className='container_right__button'>
                        <span>Sign Up</span>
                    </button>


                </div>
            </div>
        </div>
    );
}

export default Login;
