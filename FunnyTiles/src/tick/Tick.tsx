import React, { useEffect, useState } from 'react';

const TickComponent = () => {
    const [tick, setTick] = useState(0);

    useEffect(() => {
        const intervalId = setInterval(() => {
            setTick((prevTick) => prevTick + 1);
        }, 1000);


        return () => {
            clearInterval(intervalId);
        };
    }, []);

    return (
        <div>
            <p>Tick: {tick}</p>
        </div>
    );
};

export default TickComponent;


