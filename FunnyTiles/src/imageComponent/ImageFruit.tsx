import './ImageFruit.css';



function ImageFruit({ fruitName }: { fruitName: string }) {
    return (
        <div>
            <div className="image-container">
                <img src={`${process.env.PUBLIC_URL}/img/${fruitName}.jpg`} className="image-smaller" alt={fruitName} />
            </div>
        </div>
    );
}

export default ImageFruit;
