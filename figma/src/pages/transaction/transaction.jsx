import React, { useEffect, useState } from 'react'
import { Modal, Form, Button } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';
import baserequest from '../../core/baserequest';
import Navbar from '../../component/navbar/navbar';
import { useNavigate } from 'react-router-dom';
import { FcMoneyTransfer } from 'react-icons/fc'
import './transaction.scss'
import { useSelector } from "react-redux";

import Pagination from '../../component/pagination/pagination';
// import Modal from '../../component/modal/modal'
import { withRouter } from '../../router/withRouter';
const Transaction = ({ router }) => {
    const navigate = useNavigate();
    const user = useSelector(state => state.auth.user);
    //console.log(user)
    //const [user, setuser] = useState([]);
    const [to, setto] = useState("");
    const [amount, setamount] = useState(10);

    const [totalPage, setTotalpage] = useState(0);
    const [currenPage, setCurrentPage] = useState(0);

    const [desc, setdesc] = useState("");
    const [active, setactive] = useState(false);
    const [transc, settransc] = useState([]);
    const [modalShow, setModalShow] = React.useState(false);
    const test = {

        1: [], 2: [], 3: [], 4: [], 5: [], 6: [],
        7: [], 8: [], 9: [], 10: [], 11: [], 12: [],

    };

    const [test1, settest1] = useState({});
    useEffect(() => {
        if (window.localStorage.getItem("token")) {

            //getUser();
            getalltransaction();
        }
        else {
            navigate('/login')
        }
    }, [currenPage]);
    // const getUser = async () => {
    //     await baserequest.post('auth/userDetail')
    //         .then(res => {
    //             //setState(state => ({ ...state, a: props.a }));
    //             let user = res.data;
    //             setuser(state => ({ ...user }));
    //             //settransaction(res.data.transactions)
    //         })
    //         .catch(function (error) {
    //             console.log(error);
    //         });
    // }

    function getalltransaction() {
        baserequest.get('transaction/alltransaction/' + user.id + "?page=" + currenPage)
            .then(res => {
                console.log(res.data)
                // setuser(res.data);
                setTotalpage(res.data.totalPages)
                setCurrentPage(res.data.currentPage)
                settransc(res.data.transactions)
                res.data.transactions.map(item => {
                    // if(test.month.filter(item1 => item.transcmonth == item1).length > 0){
                    //     console.log(item.transcmonth)
                    // }
                    for (var property in test) {
                        if (property == item.transcmonth) {
                            //console.log("yes")
                            test[property] = [...test[property], item]
                        }
                    }
                })
                settest1(test);
                console.log(test)
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    function transaction(event) {
        event.preventDefault();
        var data = JSON.stringify({
            "to": to,
            "description": desc,
            "amount": amount,
            "TransactionCategory_id": 1
        });

        baserequest.post('transaction', data)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                getalltransaction();
                //getUser();
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    //console.log(Date.parse(transc[0].transaction_date))
    return (
        <div>

            <div className='mx-auto'>
                <div className='flex items-center max-w-6xl gap-4 mx-auto my-20'>
                    <img src='https://img.icons8.com/color/96/000000/wallet--v2.png' alt='wallet' />
                    <div>
                        <div className='text-4xl font-medium text-blue-500'>{user.username}</div>
                        <div className='text-xl font-medium text-gray-400'>Your Balance: {user.balance}$</div>
                    </div>
                </div>
                <div className='max-w-6xl mx-auto'>
                    <Form className='flex items-center' onSubmit={transaction}>

                        <Form.Group className="m-3 w-10" controlId="formBasicEmail">
                            <Form.Label>Amount</Form.Label>
                            <Form.Control value={amount} onChange={e => setamount(e.target.value)} min="10" type='number' placeholder="Enter amount" />

                        </Form.Group>

                        <Form.Group className="m-3 w-10" controlId="formBasicPassword">
                            <Form.Label>To</Form.Label>
                            <Form.Control value={to} onClick={() => setModalShow(true)} onChange={e => setto(e.target.value)} placeholder="Receiver" />
                        </Form.Group>

                        <Form.Group className="m-3 w-10" controlId="formBasicPassword">
                            <Form.Label>Description</Form.Label>
                            <Form.Control value={desc} onChange={e => setdesc(e.target.value)} placeholder="Description" />
                        </Form.Group>

                        <div className='button'>
                            <Button variant="success" type="submit">
                                Insert Transaction
                            </Button>
                        </div>

                    </Form>
                </div>
                <div className='max-w-6xl mx-auto'>
                    <div className='flex-1 text-3xl font-medium text-gray-700 pt-3'>Transaction history</div>
                    {transc != null ? (

                        Object.keys(test1).map((keyName, i) => (

                            test1[keyName].length > 0 ? (
                                <div key={i}>
                                    <div className='p-20 font-semibold text-gray-700'>Month {keyName}</div>
                                    {test1[keyName].map((item, index) => (

                                        <div key={index} className="flex p-20 items-center mb-4 bg-light text-dark">
                                            <div className='font-semibold bg-green p-2'><FcMoneyTransfer className='icon' /></div>
                                            <div className='ml-4'>
                                                <div className='text-2xl font-semibold text-gray-700'>To: {item.tos}</div>
                                                <div className=' font-semibold text-gray-400'>Type: {item.category}</div>
                                                <div className=' font-semibold text-gray-400'>{item.transaction_date}</div>
                                            </div>
                                            <div className='ml-auto text-2xl font-semibold'>
                                                Amount: {item.category == "topup" ? (<i>+</i>) : (<i>-</i>)}
                                                {item.amount}$</div>
                                        </div>

                                    ))}

                                </div>

                            ) : (
                                <p key={i}></p>
                            )
                        ))
                        // <p>{transc[0]}</p>
                        // transc.map((item, index) => (
                        //     <div key={index} className="flex p-20 items-center mb-4 bg-light text-dark">
                        //         <div className='font-semibold bg-green p-2'><FcMoneyTransfer className='icon' /></div>
                        //         <div className='ml-4'>
                        //             <div className='text-2xl font-semibold text-gray-700'>To: {item.tos}</div>
                        //             <div className=' font-semibold text-gray-400'>Type: {item.category}</div>
                        //             <div className=' font-semibold text-gray-400'>{item.transaction_date}</div>
                        //         </div>
                        //         <div className='ml-auto text-2xl font-semibold'>
                        //             Amount: {item.category == "topup" ? (<i>+</i>) : (<i>-</i>)}
                        //              {item.amount}$</div>
                        //     </div>

                        // ))

                    ) : (
                        <div className='mx-auto'>
                            <div className='text-2xl font-semibold text-center text-gray-600'>
                                No Transactions as of yet
                            </div>
                            <img
                                className='mx-auto'
                                src='https://img.icons8.com/fluent/120/000000/nothing-found.png'
                                alt='not found'
                            />
                        </div>
                    )}
                </div>
            </div>

            <MyVerticallyCenteredModal
                show={modalShow}
                setto={setto}
                setshow={setModalShow}
                onHide={() => setModalShow(false)}
            />
            {/* {active && <Modal setactive={setactive} setto={setto} />} */}

            <Pagination totalPage={totalPage} setCurrentPage={setCurrentPage} />
        </div>
    )
}

function MyVerticallyCenteredModal(props) {
    const [username, setusername] = useState("");
    const [listuser, setlistuser] = useState([]);
    useEffect(() => {
        const getUser = async () => {
            await baserequest.get('auth/listname/' + username)
                .then(res => {
                    setlistuser(res.data)
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
        getUser();


    }, [username]);
    console.log(listuser)


    function SelectTo(e, name) {
        e.preventDefault();
        props.setto(name);
        props.setshow(false);

    }
    return (
        <Modal
            show={props.show}
            onHide={props.onHide}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Receiver
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4>Chose another user to transfer</h4>
                <div className="transactionModal">
                    <div className="transactionModal__content">
                        <div className='transactionModal__content__input'>
                            <div className='transactionModal__content__input__label'>Username:</div>
                            <div className='transactionModal__content__input__searchbox'>
                            <i class="bx bx-search-alt"></i>
                                <input value={username} onChange={e => setusername(e.target.value)} placeholder='Search for username' />

                            </div>
                        </div>
                        <div className='transactionModal__content__list'>
                            {listuser != null &&
                                listuser.map((item, index) =>
                                    <div className='transactionModal__content__list__item' key={index} onClick={(e) => SelectTo(e, item.username)}>
                                        <div className='transactionModal__content__list__item__left'>{item.username}</div>
                                        <div className='transactionModal__content__list__item__right'>{item.balance} $</div>
                                    </div>
                                )

                            }
                        </div>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default withRouter(Transaction)