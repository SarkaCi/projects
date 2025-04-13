

import React, {useEffect, useState} from 'react';
import './FruitRandom.css';

function FruitRandom() {
    const arrayFruits = ['apple', 'apricot', 'banana', 'cherry', 'lemon', 'orange', 'pear', 'peach', 'plum', 'watermelon'];
    const [currentFruit, setCurrentFruit] = useState('');

    useEffect(() => {
        const timeout = setTimeout(() => {
            const shuffledFruits = [...arrayFruits].sort(() => Math.random() - 0.5);
            setCurrentFruit(shuffledFruits[0]);
        }, 1000); // Delay of 1 second

        return () => {
            clearTimeout(timeout);
        };
    }, []);

    return (
        <div className="fruit-item">
            {currentFruit && <div>{currentFruit}</div>}
        </div>
    );
}

export default FruitRandom;
