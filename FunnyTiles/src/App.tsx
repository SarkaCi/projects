import React, {useEffect, useState} from 'react';
import './App.css';
import Board from "./board/Board";
import Score from "./score/Score";
import ImageFruit from "./imageComponent/ImageFruit";
import CountDown from "./countDown/CountDown";

function App() {
    const [score, setScore] = useState(0);
    const [timeLeft, setTimeLeft] = useState(60);
    const [startNewGame, setStartNewGame] = useState(false);
    const [boardTileDate, setBoardTileDate] = useState<string[]>([]);
    const [gameRound, setGameRound] = useState(false);
    const [gameOver, setGameOver] = useState(false);
    const [correctAnswer, setCorrectAnswer] = useState('cherry');
    const [currentFruitImage, setCurrentFruitImage] = useState('cherry');
    const [level, setLevel] = useState(1);  // Track the current level
    const [levelScores, setLevelScores] = useState<number[]>([]);  // Track scores for each level
    const [totalScore, setTotalScore] = useState(0);
    const [showTotalResults, setShowTotalResults] = useState(false);  // Track whether to show total results

    const levelTimes = [60, 50, 40, 30, 20, 10];  // Time for each level
    const maxLevel = levelTimes.length;  // Total number of levels
    const boardDate = ['apple', 'banana', 'cherry', 'lemon', 'orange', 'plum', 'watermelon'];

    const handleScore = (data: string) => {
        if (data === correctAnswer) {
            setScore((score) => score + 10);
        } else {
            setScore((score) => score - 5);
        }
    };

    const handleButtonClick = () => {
        setTimeLeft(levelTimes[0]);  // Start level 1
        setScore(0);
        setGameRound(true);
        setStartNewGame(true);
        setGameOver(false);
        setLevel(1);  // Reset to level 1
        setLevelScores([]);  // Reset level scores
        setTotalScore(0);  // Reset total score
        setShowTotalResults(false);  // Reset total results view

        // Randomly select a new correct answer from the boardDate array
        const randomIndex = Math.floor(Math.random() * boardDate.length);
        const selectedFruit = boardDate[randomIndex];
        setCorrectAnswer(selectedFruit);

        // Randomly set the current fruit image
        setCurrentFruitImage(selectedFruit);
    };

    const handleNextLevel = () => {
        if (level < maxLevel && score > 0) {
            const newLevel = level + 1;
            setLevel(newLevel);
            setTimeLeft(levelTimes[newLevel - 1]);  // Set time for the new level
            setLevelScores([...levelScores, score]);  // Store the score of the current level
            setTotalScore(totalScore + score);  // Update total score
            setScore(0);  // Reset score for the new level
            setStartNewGame(true);
            setGameRound(true);
            setGameOver(false);

            // Randomly select a new correct answer
            const randomIndex = Math.floor(Math.random() * boardDate.length);
            const selectedFruit = boardDate[randomIndex];
            setCorrectAnswer(selectedFruit);
            setCurrentFruitImage(selectedFruit);
        }
    };

    const handleShowTotalResults = () => {
        setLevelScores([...levelScores, score]);  // Add the last level's score to the array
        setTotalScore(totalScore + score);  // Add the last level's score to the total score
        setShowTotalResults(true);  // Display total results
        setScore(0);  // Reset score to 0
        setTimeLeft(0);  // Reset countdown to 0
    };

    const getLevelResultMessage = (levelScore: number) => {
        if (levelScore >= 250) {
            return 'You are the master of masters!🏆🏆🏆';
        } else if (levelScore >= 200) {
            return 'You are a master!🏋️‍🎉🏆';
        } else if (levelScore >= 140) {
            return 'You are perfect! 🎉';
        } else if (levelScore >= 80) {
            return 'You are great!🎉';
        } else if (levelScore >= 20) {
            return 'You are good!👍';
        } else {
            return '💥Oh🤨....Try it again! Click new game.😎';
        }
    };

    useEffect(() => {
        if (gameRound && timeLeft > 0) {
            const timer = setInterval(() => {
                setTimeLeft((timeLeft) => timeLeft - 1);
            }, 1000);

            return () => {
                clearInterval(timer);
            };
        }
        if (!startNewGame) {
            setBoardTileDate([]);
        }
        if (timeLeft === 0) {
            setGameOver(true);
        }
    }, [gameRound, timeLeft, startNewGame]);

    return (
        <div className="App">
            <header className="App-header">Funny Tiles from Sarka - random game with 6 levels</header>
            <header className="App-banner">
                <Score scoreValue={score}/>
                <CountDown countDown={timeLeft}/>
                <ImageFruit fruitName={currentFruitImage}/>
                {/* Next Level Button (only if not the last level) */}
                {level < maxLevel && score > 0 && (
                    <button className="buttonApp" onClick={handleNextLevel}>Next Level</button>
                )}
                <button className="buttonApp" onClick={handleButtonClick}>New Game</button>


            </header>

            {gameOver && !showTotalResults && (
                <div className="gameOver">
                    <h1>LEVEL COMPLETE!</h1>
                    <h3>Your score: {score}</h3>
                    <h3>Level: {level}</h3>
                    <h3>{getLevelResultMessage(score)}</h3>  {/* Show result message for this level */}

                    {/* If it is the last level, show a button for total results */}
                    {level === maxLevel && (
                        <button className="buttonApp largeButton" onClick={handleShowTotalResults}>Show Total
                            Results</button>
                    )}
                </div>
            )}

            {gameOver && showTotalResults && (
                <div className="allScores">
                    <h2>Total Results</h2>
                    {levelScores.map((lvlScore, index) => (
                        <p key={index}>
                            Level {index + 1}: {lvlScore} - {getLevelResultMessage(lvlScore)}
                        </p>
                    ))}
                    <h2>Total Score: {totalScore}</h2>
                </div>
            )}

            {!gameOver && (
                <Board handleScore={handleScore} boardItems={boardDate} expectedItem={correctAnswer}
                       startNewGame={startNewGame}/>
            )}
        </div>
    );
}

export default App;
