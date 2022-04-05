import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form, Col, Row } from 'react-bootstrap';

import baserequest from '../../../core/baserequest';
const Bank = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [banks, setbanks] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCategories = async () => {
        await baserequest.get("bank")
            .then(res => setbanks(res.data))
    }

    const deleteCate = (id) => {
        baserequest.delete("bank/" + id)
            .then(res => {
                fetchCategories()
            })

    }

    React.useEffect(() => {
        fetchCategories();
    }, [])
    return (
        <>
            <Button variant="info m-1" onClick={e => handleShow(1)}>
                Add bank
            </Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th> Name</th>
                        <th> Img</th>
                        <th>Action</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {banks.map((item, index) => (
                        <tr key={index}>
                            <td>{item.id}</td>
                            <td>{item.name}</td>
                            <td><img style={{ height: "50px", width: "50px" }} src={item.url} /></td>

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
                setbanks={setbanks}
                setshow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                setedititem={setedititem}
            />
        </>
    )
}

function MyVerticallyCenteredModal(props) {
    const [name, setname] = useState("");
    const [img, setimg] = useState(null);
    useEffect(() => {
        if (props.edittedindex === -1) {
            setname(props.edititem.name)
        }
        return () => {
            setname("")
        }
    }, [props.show])
    const fetchCategories = async () => {
        await baserequest.get("bank")
            .then(res => props.setbanks(res.data))
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("url", img);
        data.append("name", name);
        //console.log(name,img);
        baserequest.post("bank/", data)
            .then(res => {

                fetchCategories()
            })

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        if (img != null) {
            data.append("url", img);
        }
        data.append("name", name);
        //console.log(name,img);
        baserequest.post("bank/" + props.edititem.id, data)
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
                            <label>name</label>
                            <input onChange={e => setname(e.target.value)} name="name" value={name} placeholder='name' />
                        </Form.Group>

                        <Form.Group>
                            <label>Image</label>
                            <input onChange={e => setimg(e.target.files[0])} type="file" />
                        </Form.Group>
                        <Button type='submit'>Submit</Button>
                    </Form>
                ) : (
                    <Form onSubmit={updateCate}>
                        <Form.Group>
                            <label>name</label>
                            <input onChange={e => setname(e.target.value)} name="name" value={name} placeholder='name' />
                        </Form.Group>

                        <Form.Group>
                            <label>Image</label>
                            <input onChange={e => setimg(e.target.files[0])} type="file" />
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

export default Bank