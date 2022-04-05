import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import Header from '../../component/navbar/navbar'
import { useSelector } from "react-redux";
import { Form, Button } from 'react-bootstrap'
import baserequest from '../../core/baserequest';
import './payment.scss'
import { useRef } from 'react';
const Payment = () => {
  const param = useParams();
  //console.log();
  const [desc, setdesc] = useState("");
  const [to, setto] = useState("");
  const [amount, setamount] = useState(10);
  const user = useSelector(state => state.auth.user);

  const [billinfo, setbillinfo] = useState({});
  const [customercode, setcusstomercode] = useState("");

  const [error, seterror] = useState(false);

  const buttonRef = useRef(null)
  function getbillinfo(event) {
    event.preventDefault();
    baserequest.get('bill/' + customercode  +'/category/'  + param.name)
      .then(res => {

        if (res.status == 200) {
          res.data.status == true ? seterror(true):seterror(false);
          //seterror(false);
          setbillinfo(res.data);
          console.log(Object.keys(billinfo).length)
        }
        // else{
        //   seterror(true);
        //   console.log(error)
        // }


      })
      .catch(function (error) {
        seterror(true);
        console.log(error);
      });
  }

  function transaction(event) {
    event.preventDefault();
    var data = JSON.stringify({
      "to": null,
      "description": desc,
      "amount": billinfo.amount,
      "TransactionCategory_id": 5
    });
    
    //console.log(buttonRef.current.className)
    baserequest.post('transaction/payment', data)
      .then(function (response) {
        console.log(JSON.stringify(response.data));
        buttonRef.current.className = buttonRef.current.className + ' unactive';
    buttonRef.current.setAttribute("disabled", "")
        baserequest.post('bill/setactive/'+billinfo.id)
        .then(res => {
          window.confirm("Pay successfully!");
        })
        //getalltransaction();
        //getUser();
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  return (
    <div className='p-12'>
      {/* <Header /> */}

      {/* <div>{param.name}</div> */}
      
      <div className='flex items-center max-w-6xl gap-4 mx-auto my-20'>
        
        <img src='https://img.icons8.com/color/96/000000/wallet--v2.png' alt='wallet' />
        <div>
          <div className='text-4xl font-medium text-blue-500'>{user.username}</div>
          <div className='text-xl font-medium text-gray-400'>Your Balance: {user.balance}$</div>
        </div>
      </div>
      <div className='max-w-6xl mx-auto'>
        <Form onSubmit={getbillinfo}>
          <Form.Group className="w-25" controlId="formBasicEmail">
            <Form.Label>Customer Code</Form.Label>
            <Form.Control value={customercode} onChange={e => setcusstomercode(e.target.value)} placeholder="Enter code" />

          </Form.Group>
          {error == true ? <div className='error'>Input wrong with customercode: {customercode}</div> : null}
          <div className='button'>
            <Button variant="success" type="submit">
              Continue
            </Button>
          </div>
        </Form>
        <div>
          {Object.keys(billinfo).length && error == false  ? (
            <Form className='flex items-center' onSubmit={transaction}>

              <Form.Group className="m-3 w-10" controlId="formBasicEmail">
                <Form.Label>Amount</Form.Label>
                <div>{billinfo.amount} $</div>
              </Form.Group>

              <Form.Group className="m-3 w-10" controlId="formBasicPassword">
                <Form.Label>Address</Form.Label>
                <div>{billinfo.address} </div>
              </Form.Group>

              <Form.Group className="m-3 w-10" controlId="formBasicPassword">
                <Form.Label>Description</Form.Label>
                <Form.Control value={desc} onChange={e => setdesc(e.target.value)} placeholder="Description" />
              </Form.Group>

              <div className='button'>
                <Button  ref={buttonRef} variant="success" type="submit">
                  Proceed to pay
                </Button>
              </div>

            </Form>
          ) : null}
        </div>

      </div>
    </div>
  )
}

export default Payment