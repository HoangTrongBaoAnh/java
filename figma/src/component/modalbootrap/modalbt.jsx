import { useState } from 'react';
import { useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap'
import baserequest from '../../core/baserequest';
import './modalbt.scss'

function MyVerticallyCenteredModal(props) {
    const [banks, setbank] = useState([]);
    const [bankactive, setbankactive] = useState({});
    const [cardnumber, setcardnumber] = useState("");
    const [err, seterr] = useState(false);
    const user = props.user;
    const [active, setactive] = useState(false);
    const getbanks = async () => {
        await baserequest.get("bank")
            .then(res => {
                setbank(res.data);
                console.log(res.data)
            })
    }

    const addcard = (e) => {
        e.preventDefault();
        baserequest.post(`card/addtowallet/${user.id}/bank/${bankactive.id}/cardnumber/${cardnumber}`)
            .then(res => {
                seterr(false);
                setbankactive({});
                props.setshow(false);
                props.getwallets()
                //console.log(res.data)
            })
            .catch(err => {
                seterr(true);
                console.log(err)
            })
        //props.setshow(false)

    }
    const handle = (e,item) => {
        e.preventDefault();
        setbankactive(item);
        setactive(true);
        //console.log(bankactive)
    }
    useEffect(() => {
        var bankss = document.getElementsByClassName('bankbody');
        for (var i = 0; i < bankss.length; i++) {
            bankss[i].addEventListener("click", function() {
            
                var b = document.querySelector(".bankbody.active");
                console.log(b)
                if (b) b.classList.remove("active");
                this.classList.add('active');
            });
          }
        getbanks();
        if(props.show === false){
            setbankactive({});
            seterr(false)
        }
        //console.log(props.show);
    }, [props.show])

    // useEffect(()=>{
    //     setbankid(0);
    //     //console.log(banksid)
    // },[props.modalShow])

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
                    Modal heading
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4>Choose your bank</h4>
                <div className='bank'>
                    {
                        banks.length > 0 ? (
                            banks.map((item, index) => (
                                <div key={index} className="bankbody" onClick={e => handle(e,item)}>
                                    <img src={item.url} />
                                    <div className='bankbody__banktitle'>{item.name}</div>

                                </div>
                            ))
                        )
                            : null
                    }
                </div>
                {
                    Object.keys(bankactive).length > 0 ? (
                        <div className='cardnumber'>
                            <div className='cardnumber__content'>
                                <Form onSubmit={addcard}>
                                    <div>CardNumber</div>
                                    <div className="cardnumber__content__form">
                                    <input value={cardnumber} onChange={e => setcardnumber(e.target.value)} type="text" />
                                    <div><Button type='submit'>Add</Button></div>
                                    </div>
                                </Form>
                            </div>
                            {err ? <div className='cardnumber__error'>wrong card number: {cardnumber}</div> : null}
                        </div>
                    ) : null
                }
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}


export default MyVerticallyCenteredModal;