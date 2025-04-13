import React from 'react';
import './Button.css';

interface ClickProps{
    handleButtonClick: ()=>void;
   }

function Button(props:ClickProps) {

    return (
        <button className="button" onClick={props.handleButtonClick}>Start</button>
    );
}

export default Button;

