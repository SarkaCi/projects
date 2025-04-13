import React, {useEffect, useState} from 'react';
import './Board.css';

interface BoardProps {
    handleScore: (data: string) => void;
    boardItems: string[];
    expectedItem: string;
    startNewGame: boolean;

}

function Board(props: BoardProps) {

    const numTiles = 24;
    const [tileData, setTileData] = useState<string[]>([]);


    useEffect(() => {
        if (props.startNewGame) {
        const generateTileDate = () => {
            const generatedTileDate = Array.from({length: numTiles}, () => (
                props.boardItems[Math.floor(Math.random() * (props.boardItems.length + 1))]
            ));
            setTileData(generatedTileDate)
        };

            generateTileDate();
        } else {
            setTileData([]);
        }

    } , [props.startNewGame, props.boardItems]);


    const renderTiles = () => {


        return tileData.map((tile, index) => (

            <div key={index} className="tile" onClick={() => props.handleScore(tile)}>{tile}</div>
        ));
    };

    return (
        <div>
            <div className="board">{renderTiles()}</div>
        </div>
    );
}


export default Board;