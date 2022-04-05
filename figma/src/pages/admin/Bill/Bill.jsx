import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form, Col, Row } from 'react-bootstrap';

import baserequest from '../../../core/baserequest';
const Bill = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [bills, setbills] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchBill = async () => {
        await baserequest.get("bill")
            .then(res => setbills(res.data))
    }

    const deleteCate = (id) => {
        baserequest.delete("bill/" + id)
            .then(res => {
                fetchBill()
            })

    }

    React.useEffect(() => {
        fetchBill();
    }, [])
    return (
        <>
            <Button variant="info m-1" onClick={e => handleShow(1)}>
                Add bill
            </Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Customer code</th>
                        <th>Address</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Category</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    {bills.map((item, index) => (
                        <tr key={index}>
                            <td>{item.customercode}</td>
                            <td>{item.address}</td>
                            <td>{item.amount}</td>
                            <td>{item.status ? (<div>true</div>) : <>false</>}</td>
                            <td>{item.cagetory_id}</td>
                            <td>
                                <button onClick={e => handleShow(-1, item)}><i className='bx bx-edit'></i>Edit</button>
                                <button onClick={e => deleteCate(item.id)}><i className='bx bx-trash'></i></button>
                            </td>
                        </tr>
                    ))}

                </tbody>
            </Table>
            <MyVerticallyCenteredModal
                show={show}
                setbills={setbills}
                setshow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                fetchBill={fetchBill}
                setedititem={setedititem}
            />
        </>
    )
}

function MyVerticallyCenteredModal(props) {
    const [customercode, setcustomercode] = useState("");
    const [address, setaddress] = useState("");
    const [amount, setamount] = useState(0);
    const [categoryid, setcategoryid] = useState("");

    const [category, setcategory] = useState([]);
    useEffect(() => {
        fetchCategories();
        //console.log(props.edititem)
        if (props.edittedindex === -1) {
            setcustomercode(props.edititem.customercode)
            setaddress(props.edititem.address)
            setamount(props.edititem.amount)
            setcategoryid(props.edititem.cagetory_id)
        }
        return () => {
            // setname("")
        }
    }, [props.show])

    const fetchCategories = async () => {
        await baserequest.get("category")
            .then(res => setcategory(res.data))
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("customer_code", customercode);
        data.append("address", address);
        data.append("amount", amount);
        data.append("category_id", categoryid);

        // console.log(categoryid);
        baserequest.post("bill/", data)
            .then(res => {

                props.fetchBill()
            })

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();

        data.append("customer_code", customercode);
        data.append("address", address);
        data.append("amount", amount);
        data.append("category_id", categoryid);
        //console.log(name,img);
        baserequest.post("bill/" + props.edititem.id, data)
            .then(res => {

                fetchCategories()
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
                <Modal.Title>{props.edittedindex === 1 ? "Add new Category" : "Edit"}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {props.edittedindex === 1 ? (
                    <Form onSubmit={createCate}>
                        <Form.Group>
                            <label>customercode</label>
                            <input onChange={e => setcustomercode(e.target.value)} name="customercode" value={customercode} placeholder='customercode' />
                        </Form.Group>

                        <Form.Group>
                            <label>address</label>
                            <input onChange={e => setaddress(e.target.value)} name="address" value={address} placeholder='address' />
                        </Form.Group>
                        <Form.Group>
                            <label>amount</label>
                            <input onChange={e => setamount(e.target.value)} name="amount" value={amount} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <label>Category</label>
                            <select onChange={e => setcategoryid(e.target.value)} name="updateCate" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group >
                        <Button type='submit'>Submit</Button>
                    </Form >
                ) : (
                    <Form onSubmit={updateCate}>
                        <Form.Group>
                            <label>customercode</label>
                            <input onChange={e => setcustomercode(e.target.value)} name="customercode" value={customercode} placeholder='customercode' />
                        </Form.Group>

                        <Form.Group>
                            <label>address</label>
                            <input onChange={e => setaddress(e.target.value)} name="address" value={address} placeholder='address' />
                        </Form.Group>
                        <Form.Group>
                            <label>amount</label>
                            <input onChange={e => setamount(e.target.value)} name="amount" value={amount} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <label>Category</label>
                            <select defaultValue={categoryid} onChange={e => setcategoryid(e.target.value)} name="bank" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group >
                        <Button type='submit'>Submit</Button>
                    </Form >
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
export default Bill