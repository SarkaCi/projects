import './Score.css';
import React from 'react';



interface ScoreProps {
    scoreValue: number
}

function Score(props: ScoreProps) {

const {scoreValue}= props



    return (
        <div>
                <h2 className="score">score {scoreValue}</h2>
        </div>

    );
}

export default Score;
