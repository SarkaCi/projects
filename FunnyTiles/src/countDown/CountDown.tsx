import './CountDown.css';

interface CountDownProps {
    countDown: number;

}

const Countdown = (props: CountDownProps) => {
    const {countDown} = props

    return (
        <div>
            <h2 className="countDown" > count down {countDown} s </h2>
        </div>
    );
};

export default Countdown;
