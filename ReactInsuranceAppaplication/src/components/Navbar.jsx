import { Link } from "react-router-dom";
import { useState } from "react";
import { Menu, X } from "lucide-react";


/**
 * Navigační lišta aplikace.
 * - Obsahuje odkaz na domovskou stránku a seznam pojištění/pojištěných osob.
 * - Na mobilních zařízeních se zobrazí hamburger menu.
 */
const Navbar = () => {
    const [isOpen, setIsOpen] = useState(false); // Stav pro otevření/zavření menu

    return (
        <nav className="fixed top-0 left-0 w-full bg-gradient-to-r from-gray-900 via-gray-800 to-gray-700 shadow-lg z-50">
            <div className="container mx-auto flex items-center justify-between px-6 py-4">
                <Link className="text-white text-2xl font-bold tracking-wide hover:text-gray-300 transition" to="/">
                    Aplikace Pojištění od ITS
                </Link>
                <button
                    className="lg:hidden text-white focus:outline-none"
                    onClick={() => setIsOpen(!isOpen)}
                >
                    {isOpen ? <X size={28} /> : <Menu size={28} />}
                </button>

                <div className={`lg:flex lg:items-center lg:space-x-6 ${isOpen ? "block" : "hidden"} absolute lg:static top-16 left-0 w-full lg:w-auto bg-gray-900 lg:bg-transparent shadow-lg lg:shadow-none p-6 lg:p-0 transition-all`}>
                    <ul className="flex flex-col lg:flex-row lg:space-x-6 text-white">
                        <li>
                            <Link className="block py-2 lg:py-0 text-lg hover:text-gray-300 transition" to="/insurance">Seznam pojištění</Link>
                        </li>
                        <li>
                            <Link className="block py-2 lg:py-0 text-lg hover:text-gray-300 transition" to="/insured-persons">Seznam pojištěných osob</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
