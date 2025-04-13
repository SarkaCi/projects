import { Link } from "react-router-dom";
import welcomeImage from '../assets/pictures/welcomepage.png'; // Import obrázku

/**
 * Hlavní stránka aplikace.
 * - Obsahuje pozadí s uvítacím obrázkem.
 * - Nabízí dvě tlačítka pro navigaci na pojištěné osoby a pojištění.
 */
const Home = () => {
    return (
        <div
            className="relative flex flex-col items-center justify-center min-h-screen bg-cover bg-center bg-no-repeat"
            style={{ backgroundImage: `url(${welcomeImage})` }}
        >
            {/* Overlay pro tmavší pozadí */}
            <div className="absolute inset-0 bg-black opacity-50"></div>

            {/* Obsah vycentrovaný na střed */}
            <div className="relative z-10 text-center w-full px-4 sm:px-6 md:px-12 py-16">
                <h1 className="text-4xl sm:text-5xl font-bold text-white mb-4">
                    Vítejte v aplikaci <span className="text-blue-500">Pojištění od ITS</span>
                </h1>
                <p className="text-white text-lg sm:text-xl mb-6">
                    Spravujte své pojištění snadno a rychle.
                </p>

                <div className="flex flex-col sm:flex-row gap-4 justify-center">
                    <Link
                        to="/insured-persons"
                        className="w-full sm:w-auto bg-blue-600 text-white px-6 py-3 rounded-lg shadow-md hover:bg-blue-700 transition"
                    >
                        Pojištěná osoba
                    </Link>
                    <Link
                        to="/insurance"
                        className="w-full sm:w-auto bg-green-600 text-white px-6 py-3 rounded-lg shadow-md hover:bg-green-700 transition"
                    >
                        Pojištění
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Home;
