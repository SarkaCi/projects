import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

/**
 * Komponenta pro editaci pojištěné osoby.
 * - Načítá informace o pojištěné osobě podle ID z API.
 * - Umožňuje uživateli aktualizovat údaje a odeslat změny na server.
 */
const EditInsuredPerson = () => {
    const { id } = useParams(); // Získání ID z URL
    const navigate = useNavigate();

    const [person, setPerson] = useState({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        dateOfBirth: "",
        address: {
            street: "",
            streetNumber: "",
            city: "",
            postalCode: "",
            country: ""
        }
    });

    /**
     * Načítání dat při načtení stránky.
     */
    useEffect(() => {
        fetch(`/api/insured-persons/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Osoba nenalezena");
                }
                return response.json();
            })
            .then(data => setPerson(data))
            .catch(error => {
                console.error("Chyba při načítání osoby:", error);
                alert("Osoba nenalezena.");
                navigate("/insured-persons"); // Přesměrování zpět na seznam
            });
    }, [id, navigate]);

    /**
     * Zpracování změn v obecných polích formuláře.
     */
    const handleChange = (e) => {
        const { name, value } = e.target;
        setPerson(prev => ({ ...prev, [name]: value }));
    };

    /**
     * Zpracování změn v polích adresy.
     */
    const handleAddressChange = (e) => {
        const { name, value } = e.target;
        setPerson(prev => ({
            ...prev,
            address: { ...prev.address, [name]: value }
        }));
    };

    /**
     * Odeslání formuláře na server.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`/api/insured-persons/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(person),
            });

            if (response.ok) {
                navigate("/insured-persons"); // Přesměrování zpět na seznam
            } else {
                alert("Chyba při ukládání změn.");
            }
        } catch (error) {
            console.error("Chyba při odesílání dat:", error);
        }
    };

    return (
        <div className="max-w-4xl mx-auto my-20 p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Editace pojištěné osoby</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Jméno */}
                <div>
                    <label className="block text-gray-700 font-medium">Jméno</label>
                    <input
                        type="text"
                        name="firstName"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.firstName}
                        onChange={handleChange}
                        required
                    />
                </div>

                {/* Příjmení */}
                <div>
                    <label className="block text-gray-700 font-medium">Příjmení</label>
                    <input
                        type="text"
                        name="lastName"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.lastName}
                        onChange={handleChange}
                        required
                    />
                </div>

                {/* Email */}
                <div>
                    <label className="block text-gray-700 font-medium">Email</label>
                    <input
                        type="email"
                        name="email"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.email}
                        onChange={handleChange}
                        required
                    />
                </div>

                {/* Telefon */}
                <div>
                    <label className="block text-gray-700 font-medium">Telefon</label>
                    <input
                        type="text"
                        name="phone"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.phone}
                        onChange={handleChange}
                        required
                    />
                </div>

                {/* Datum narození */}
                <div>
                    <label className="block text-gray-700 font-medium">Datum narození</label>
                    <input
                        type="date"
                        name="dateOfBirth"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.dateOfBirth}
                        onChange={handleChange}
                        required
                    />
                </div>

                <h4 className="text-xl font-semibold text-gray-700 mt-6">Adresa</h4>

                {/* Adresa */}
                <div>
                    <label className="block text-gray-700 font-medium">Ulice</label>
                    <input
                        type="text"
                        name="street"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.address.street}
                        onChange={handleAddressChange}
                        required
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium">Číslo popisné</label>
                    <input
                        type="text"
                        name="streetNumber"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.address.streetNumber}
                        onChange={handleAddressChange}
                        required
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium">Město</label>
                    <input
                        type="text"
                        name="city"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.address.city}
                        onChange={handleAddressChange}
                        required
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium">PSČ</label>
                    <input
                        type="text"
                        name="postalCode"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.address.postalCode}
                        onChange={handleAddressChange}
                        required
                    />
                </div>

                <div>
                    <label className="block text-gray-700 font-medium">Stát</label>
                    <input
                        type="text"
                        name="country"
                        className="w-full p-2 border border-gray-300 rounded-md"
                        value={person.address.country}
                        onChange={handleAddressChange}
                        required
                    />
                </div>

                {/* Tlačítka pro odeslání a zrušení */}
                <div className="flex justify-between">
                    <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-700 transition">
                        Uložit změny
                    </button>
                    <button
                        type="button"
                        className="bg-gray-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-gray-700 transition"
                        onClick={() => navigate("/insured-persons")}
                    >
                        Zrušit
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EditInsuredPerson;
