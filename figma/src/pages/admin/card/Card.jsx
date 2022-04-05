import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form } from 'react-bootstrap';

import baserequest from '../../../core/baserequest';

const Card = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [card, setcard] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCards = async () => {
        await baserequest.get("card")
            .then(res => {
                setcard(res.data);
                console.log(res.data)
            })
    }

    const deleteCate = (id) => {
        baserequest.delete("card/" + id)
            .then(res => {

                fetchCards()
            })

    }

    React.useEffect(() => {
        fetchCards();
    }, [])
    return (
        <>
            <Button variant="info m-1" onClick={e => handleShow(1)}>
                Add card
            </Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th> cardnumber</th>
                        <th> balance</th>
                        <th> bank_id</th>
                        <th>Action</th>
                        
                    </tr>
                </thead>
                <tbody>
                    {card.map((item, index) => (
                        <tr key={index}>
                            <td>{item.id}</td>
                            <td>{item.cardnumber}</td>
                            <td>{item.balance}</td>
                            <td>{item.bank_id}</td>
                            <td>
                                <button onClick={e => handleShow(-1, item)}><i className='bx bx-edit'></i>Edit</button>
                                <button onClick={e => deleteCate(item.id)}><i className='bx bx-trash'></i></button></td>
                            
                        </tr>
                    ))}

                </tbody>
            </Table>
            <MyVerticallyCenteredModal
                show={show}
                setcard={setcard}
                setshow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                fetchCards={fetchCards}
                setedititem={setedititem}
            />
        </>
    )
}

function MyVerticallyCenteredModal(props) {
    const [cardnumber, setcardnumber] = useState("");
    const [securitycode, setsecuritycode] = useState("");
    const [balance, setbalance] = useState(0);
    const [bank_id, setbank_id] = useState(0);

    const [category, setcategory] = useState([]);
    useEffect(() => {
        fetchCategories();

    }, [])


    useEffect(() => {
        console.log(props.edittedindex , bank_id)
        const f = async () => {
            if (props.edittedindex === -1) {
            
                setcardnumber(props.edititem.cardnumber)
                setbalance(props.edititem.balance);
                setsecuritycode(props.edititem.securitycode)
                setbank_id(props.edititem.bank_id)
            }
        }
        f();
        return () => {
            setcardnumber("")
            setbalance(0);
            setsecuritycode("")
            //setbank_id(0)
        }
    }, [props])
    const fetchCategories = async () => {
        await baserequest.get("bank")
            .then(res => {
                //console.log(res.data)
                setcategory(res.data);
                //setbank_id(res.data[0].id)
            })
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        //data.append("url", img);
        data.append("cardnumber", cardnumber);
        data.append("balance", balance);
        data.append("securitycode", securitycode);
        data.append("bank_id", bank_id);

        baserequest.post("card", data)
            .then(res => {
                props.fetchCards();
            })
            .catch(err => console.log(err))

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("cardnumber", cardnumber);
        data.append("balance", balance);
        data.append("securitycode", securitycode);
        data.append("bank_id", bank_id);
        baserequest.post("card/" + props.edititem.id, data)
            .then(res => {

                props.fetchCards();
            })

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
                <Modal.Title>{props.edittedindex === 1 ? "Add new Card" : "Edit"}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {props.edittedindex === 1 ? (
                    <Form onSubmit={createCate}>
                        <Form.Group>
                            <label>bank</label>
                            <select options={bank_id} onChange={e => setbank_id(e.target.value)} name="bank" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group>

                        <Form.Group>
                            <label>cardnumber</label>
                            <input onChange={e => setcardnumber(e.target.value)} name="cardnumber" value={cardnumber} placeholder='cardnumber' />
                        </Form.Group>

                        <Form.Group>
                            <label>balance</label>
                            <input onChange={e => setbalance(e.target.value)} name="balance" value={balance} placeholder='balance' />
                        </Form.Group>

                        <Form.Group>
                            <label>securitycode</label>
                            <input onChange={e => setsecuritycode(e.target.value)} name="securitycode" value={securitycode} placeholder='securitycode' />
                        </Form.Group>
                        <Button type='submit'>Submit</Button>
                    </Form>
                ) : (
                    <Form onSubmit={updateCate}>

                        <Form.Group>
                            <label>bank</label>
                            <select defaultValue={bank_id} onChange={e => setbank_id(e.target.value)} name="bank" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group>

                        <Form.Group>
                            <label>cardnumber</label>
                            <input onChange={e => setcardnumber(e.target.value)} name="cardnumber" value={cardnumber} placeholder='cardnumber' />
                        </Form.Group>

                        <Form.Group>
                            <label>balance</label>
                            <input onChange={e => setbalance(e.target.value)} name="balance" value={balance} placeholder='balance' />
                        </Form.Group>

                        <Form.Group>
                            <label>securitycode</label>
                            <input onChange={e => setsecuritycode(e.target.value)} name="securitycode" value={securitycode} placeholder='securitycode' />
                        </Form.Group>

                        <Button type='submit'>Submit</Button>
                    </Form>
                )}

            </Modal.Body>
            <Modal.Footer>
                <Button onClick={e => props.setshow(false)} variant="secondary" >
                    Close
                </Button>

            </Modal.Footer>
        </Modal>
    );
}

export default Card