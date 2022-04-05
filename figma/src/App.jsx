import './App.scss';
import NavBar from './component/navbar/navbar'
import baserequest from './core/baserequest'
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from 'react'
import { Form, Button, Tabs, Tab } from 'react-bootstrap'
import { useDispatch } from "react-redux";
import { FcMoneyTransfer } from 'react-icons/fc'

import MyVerticallyCenteredModal from './component/modalbootrap/modalbt';

import { fetchUserAsync, UPDATE_USER_STATE } from './store/auth/authreducer';
import 'bootstrap/dist/css/bootstrap.min.css';
import * as action from './store/auth/authaction'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux';
import transaction from './pages/transaction/transaction';
const App = () => {
  const dispatch = useDispatch();
  // const updateStoreUser = (item) => {
  //   dispatch(UPDATE_USER_STATE(item))
  // }
  const navigate = useNavigate();
  const user = useSelector(state => state.auth.user);
  //const [user, setuser] = useState([]);
  const [category, setcategory] = useState([]);
  const [balance, setbalance] = useState(10);
  const [withdraw, setwithdraw] = useState(10);
  // const length = useSelector(state => state.auth.transc.length)
  // const transc = useSelector(state => state.auth.transc).slice(length - 4, length);
  const [transc, settransc] = useState([]);

  const [modalShow, setModalShow] = React.useState(false);
  const [activewallet, setactivewallet] = useState({});
  const [wallet, setwallet] = useState([]);
  //var transc = [];
  //console.log(transc)
  useEffect(() => {
    if (window.localStorage.getItem("token")) {
      dispatch(fetchUserAsync());
      getUser();
      getserviceCategory();
      getwallets()
    }
    else {
      //navigate('/login')
    }
  }, [dispatch]);

  // useEffect(() => {
  //   if (user) {
  //     getUser
  //   }

  // }, [user])
  function getalltransaction(user) {
    baserequest.get('transaction/alltransaction/' + user + "?page=" + 0)
      .then(res => {
        console.log(res.data)
        // setuser(res.data);
        settransc(res.data.transactions)

      })
      .catch(function (error) {
        console.log(error);
      });

  }

  const getwallets = async () => {
    await baserequest.get('card/wallet')
      .then(res => {
        setwallet(res.data)
        console.log(res.data)
        // setuser(res.data);
        res.data.map(item => {
          if (item.active) {
            setactivewallet(item);
          }
        })
      })
      .catch(function (error) {
        console.log(error);
      });

  }

  const getUser = async () => {
    await baserequest.post('auth/userDetail')
      .then(res => {
        getalltransaction(res.data.id)
        //updateStoreUser(res.data);
        //setuser(res.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  const getserviceCategory = async () => {
    await baserequest.get('category')
      .then(res => {
        console.log(res.data);
        setcategory(res.data)
        // updateStoreUser(res.data);
        // setuser(res.data);
      })
      .catch(function (error) {
        console.log(error);
      });
    // category.map(item => {
    //   service.push({
    //     id:item.id,
    //     display:item.name,
    //     icon: item.name == "water" ? <i className='bx bx-water'></i> : <i className='bx bx-alarm'></i>
    //   })
    // })

  }

  // function transaction(event) {
  //   event.preventDefault();
  //   var data = JSON.stringify({
  //     "to": user.name,
  //     "description": "Nạp tiền",
  //     "amount": balance,
  //     "TransactionCategory_id": 2
  //   });

  //   baserequest.post('transaction', data)
  //     .then(function (response) {
  //       console.log(JSON.stringify(response.data));
  //       //getalltransaction();
  //       //getUser();
  //       dispatch(fetchUserAsync());
  //     })
  //     .catch(function (error) {
  //       console.log(error);
  //     });
  // }

  function cashin(event) {
    event.preventDefault();
    var data = JSON.stringify({
      "to": user.username,
      "description": "Nạp tiền",
      "amount": balance,
      "TransactionCategory_id": 2
    });

    baserequest.post('transaction/cashin/' + activewallet.cardNumber, data)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        dispatch(fetchUserAsync());
        getwallets()
        getalltransaction(user.id);
        //getUser();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  }

  function cashout(event) {
    event.preventDefault();
    var data = JSON.stringify({
      "to": user.name,
      "description": "rút tiền",
      "amount": withdraw,
      "TransactionCategory_id": 4
    });

    baserequest.post('transaction/cashin/' + activewallet.cardNumber, data)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        getalltransaction(user.id);
        getwallets()
        dispatch(fetchUserAsync());
        //getUser();
      })
      .catch(function (error) {
        console.log(error.response);
      });
  }

  const removewallet = (e, item) => {
    //e.preventDefault;
    //console.log(item)
    if (window.confirm("Delete the item?")) {
      baserequest.post('card/remove/wallet/' + item.cardNumber)
        .then(function (response) {
          console.log(response)
          getwallets()
        })
        .catch(function (error) {
          console.log(error)
        });
    }

  }

  const setwalletactive = (e, item) => {
    if (window.confirm("set acive this card")) {
      baserequest.post('card/wallet/' + item.cardNumber)
        .then(function (response) {
          console.log(response)
          getwallets()
        })
        .catch(function (error) {
          console.log(error)
        });
    }
  }

  return (
    <div className="App">


      <div className='row app__section'>
        <div className='col-lg-8 app__section__left'>
          <div className='app__section__left__wallet'>
            <div className="app__section__left__wallet__header">
              <h2>My wallet</h2>
              <div className='ml-auto'>
                <Button variant="primary" onClick={() => setModalShow(true)}>
                  <i className='bx bx-plus'></i>
                </Button>

                <MyVerticallyCenteredModal
                  user={user}
                  setshow={setModalShow}
                  getwallets={getwallets}
                  show={modalShow}
                  onHide={() => setModalShow(false)}
                />

              </div>

            </div>
            {activewallet != null ? (
              <div className='app__section__left__wallet__card'>
                <div className="app__section__left__wallet__card__bankname">
                  <i className='bx bxs-bank'></i> Bank_name
                </div>
                <div className='app__section__left__wallet__card__chip'>

                </div>
                <div className="app__section__left__wallet__card__cardnumber">
                  {activewallet.cardNumber}
                </div>
                <div className="app__section__left__wallet__card__bottom">
                  <div>
                    {user.username}
                  </div>
                  <div>
                    VISA
                  </div>
                </div>
              </div>

            ) : null}
            <div className="app__section__left__wallet__content">
              <h2>All wallets</h2>

              {wallet.length > 0 ? (
                wallet.map((item, index) => (
                  <div key={index} className={`app__section__left__wallet__list ${item.active ? 'active' : ''}`}>
                    <div  className='app__section__left__wallet__list__info'>
                      <div className="app__section__left__wallet__list__info__cardnumber">
                        Card Number: {item.cardNumber}
                      </div>
                      <div className="app__section__left__wallet__list__info__balance">
                        Current balance: {item.balance} $
                      </div>
                    </div>
                    <div>
                      <Form.Check
                        checked={item.active}
                        onChange={e => setwalletactive(e, item)}
                        type="switch"
                        id="custom-switch"
                        label="set active"
                      />
                    </div>

                    <div className="app__section__left__wallet__list__info__button">
                      <div>Remove</div>
                      <button onClick={e => removewallet(e, item)}><i className='bx bx-minus'></i></button>
                    </div>
                  </div>
                ))
              ) : <div>Not have any wallet</div>}
            </div>
          </div>
          <div className='App__title'>
            <div><i className='bx bx-wallet'></i>Your balance: {user.balance} $</div>
          </div>
          <div className='app__section__left__transaction'>
            <div className='app__section__left__transaction__header' >
              <div className='app__section__left__transaction__header__title'>
                <div>Lasted Transaction</div>

              </div>
              <div className='app__section__left__transaction__header__button'>
                <Link to='/transaction'>View All</Link>

              </div>

            </div>
            <div className="app__section__left__transaction__content">
              {
                transc.length > 0 ? (
                  transc.map((item, index) => (
                    <div key={index} className="flex p-20 items-center mb-4 bg-light text-dark">
                      <div className='font-semibold bg-green p-2'><FcMoneyTransfer className='icon' /></div>
                      <div className='ml-4'>
                        <div className=' font-semibold '>{item.froms}</div>
                        <div className=' font-semibold text-gray-400'>{item.transaction_date}</div>
                      </div>
                      <div className='ml-auto text-2xl font-semibold'>
                        Amount: {item.category == "topup" ? (<i>+</i>) : (<i>-</i>)}
                        {item.amount}$</div>
                    </div>
                  ))
                ) : null
              }
            </div>
          </div>
        </div>
        <div className='col-lg-4 app__section__right'>
          <div className="app__section__right__cash">
            <Tabs
              defaultActiveKey="home"
              transition={false}
              id="noanim-tab-example"

            >
              <Tab eventKey="home" title="Cash In">
                <Form className='' onSubmit={cashin}>

                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control value={balance} onChange={e => setbalance(e.target.value)} min="10" type='number' placeholder="Enter amount" />

                  </Form.Group>
                  <div className='button'>
                    <Button variant="success" type="submit">
                      Cash in
                    </Button>
                  </div>

                </Form>
              </Tab>
              <Tab eventKey="profile" title="Cash out">
                <Form className='' onSubmit={cashout}>

                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control value={withdraw} onChange={e => setwithdraw(e.target.value)} min="10" type='number' placeholder="Enter amount" />

                  </Form.Group>
                  <div className='button'>
                    <Button variant="warning" type="submit">
                      Cash out
                    </Button>
                  </div>

                </Form>
              </Tab>

            </Tabs>
          </div>

          <div className="app__section__right__service">
            <div className='app__section__right__service__title'>Service</div>
            {category.length < 0 ? (<div>not found</div>) : (
              category.map((item, index) => (
                <Link key={index} to={`/payment/` + item.id} >
                  <div className='app__section__right__service__item' >
                    <div><img className='app__section__right__service__item__img' src={item.url} /></div>
                    <div className='ml-auto'>{item.name} <span><i className='bx bx-right-arrow'></i></span></div>
                  </div>
                </Link>

              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
