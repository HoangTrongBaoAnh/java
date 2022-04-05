import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form, Col, Row } from 'react-bootstrap';

import baserequest from '../../../core/baserequest';

const BillCategory = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [category, setcategory] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCategories = async () => {
        await baserequest.get("category")
            .then(res => setcategory(res.data))
    }

    const deleteCate = (id) => {
        baserequest.delete("category/" + id)
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
                Add category
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
                    {category.map((category, index) => (
                        <tr key={index}>
                            <td>{category.id}</td>
                            <td>{category.name}</td>
                            <td><img style={{height:"50px",width:"50px"}} src={category.url}/></td>

                            <td><button onClick={e => handleShow(-1, category)}><i className='bx bx-edit'></i>Edit</button></td>
                            <td><button onClick={e => deleteCate(category.id)}><i className='bx bx-trash'></i></button></td>
                        </tr>
                    ))}

                </tbody>
            </Table>
            <MyVerticallyCenteredModal
                show={show}
                setcategory={setcategory}
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
    const [img, setimg] = useState("");
    useEffect(() => {
        if (props.edittedindex === -1) {
            setname(props.edititem.name)
        }
        return () => {
            setname("")
        }
    }, [props.show])
    const fetchCategories = async () => {
        await baserequest.get("category")
            .then(res => props.setcategory(res.data))
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("url", img);
        data.append("name", name);
        //console.log(name,img);
        baserequest.post("category/", data)
            .then(res => {

                fetchCategories()
            })

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("url", img);
        data.append("name", name);
        //console.log(name,img);
        baserequest.post("category/" + props.edititem.id, data)
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
                <Button onClick={e=>props.setshow(false)} variant="secondary" >
                    Close
                </Button>

            </Modal.Footer>
        </Modal>
    );
}

export default BillCategory