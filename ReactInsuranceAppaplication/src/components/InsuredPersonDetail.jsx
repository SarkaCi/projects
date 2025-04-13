import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { Pencil, Trash, PlusCircle, ArrowLeft } from "lucide-react"; // Moderní ikony

/**
 * Komponenta pro zobrazení detailu pojištěné osoby.
 * - Načítá informace o osobě a jejích pojištěních z API.
 * - Umožňuje mazání pojištění a navigaci k editaci nebo přidání pojištění.
 * - Automaticky přepíná mezi mobilním (karty) a desktopovým (tabulka) zobrazením.
 *
 * @param {boolean} isMobile - Určuje, zda se komponenta zobrazí v mobilním režimu.
 */
const InsuredPersonDetail = ({ isMobile }) => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [person, setPerson] = useState(null);
    const [insurances, setInsurances] = useState([]);

    useEffect(() => {
        fetch(`/api/insured-persons/${id}`)
            .then(response => response.json())
            .then(data => setPerson(data))
            .catch(error => console.error("Chyba při načítání osoby:", error));

        fetch(`/api/insured-persons/${id}/insurances`)
            .then(response => response.json())
            .then(data => setInsurances(data))
            .catch(error => console.error("Chyba při načítání pojištění osoby:", error));
    }, [id]);

    /**
     * Odebere pojištění z pojištěné osoby.
     * @param {number} insuranceId - ID pojištění k odstranění.
     */
    const removeInsurance = (insuranceId) => {
        fetch(`/api/insured-persons/${id}/remove-insurance/${insuranceId}`, { method: "DELETE" })
            .then(response => {
                if (response.ok) {
                    setInsurances(insurances.filter(ins => ins.id !== insuranceId));
                }
            })
            .catch(error => console.error("Chyba při odebírání pojištění:", error));
    };

    if (!person) return <p className="text-center text-gray-600 mt-10">Načítání...</p>;

    return (
        <div className="max-w-4xl mx-auto my-20 p-6 bg-white/80 shadow-xl rounded-lg border border-gray-200">
            {/* Nadpis */}
            <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Detail pojištěné osoby</h2>

            {/* Informace o osobě */}
            <div className="bg-gray-50 p-6 rounded-lg shadow-md">
                <p className="text-lg"><strong>Jméno:</strong> {person.firstName} {person.lastName}</p>
                <p className="text-lg"><strong>Email:</strong> {person.email}</p>
                <p className="text-lg"><strong>Telefon:</strong> {person.phone}</p>
                <p className="text-lg"><strong>Datum narození:</strong> {person.dateOfBirth}</p>

                {/* Adresa */}
                {person.address ? (
                    <div className="mt-4">
                        <p className="text-lg"><strong>Adresa</strong></p>
                        <p>{person.address.street} {person.address.streetNumber}, {person.address.city}</p>
                        <p>{person.address.postalCode}, {person.address.country}</p>
                    </div>
                ) : (
                    <p className="text-gray-500">Adresa: Neuvedena</p>
                )}
            </div>

            {/* Pojištění */}
            <h4 className="text-xl font-semibold text-gray-700 mt-6">Pojištění</h4>

            {/* Dynamické zobrazení pro mobilní zařízení */}
            {isMobile ? (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                    {insurances.length > 0 ? (
                        insurances.map(insurance => (
                            <div key={insurance.id} className="bg-white shadow-md rounded-lg p-4">
                                <h5 className="text-lg font-semibold">{insurance.policyNumber}</h5>
                                <p>{insurance.type}</p>
                                <p>{insurance.amount} Kč</p>
                                <p>{insurance.validFrom} - {insurance.validTo}</p>
                                <div className="mt-4 flex justify-between gap-2">
                                    <Link to={`/insured-persons/${id}/edit-insurance/${insurance.id}`}
                                          className="bg-yellow-500 text-white px-4 py-2 rounded-md">Editovat</Link>
                                    <button onClick={() => removeInsurance(insurance.id)}
                                            className="bg-red-600 text-white px-4 py-2 rounded-md">Smazat</button>
                                </div>
                            </div>
                        ))
                    ) : (
                        <p className="text-center text-gray-500">Žádné pojištění</p>
                    )}
                </div>
            ) : (
                <div className="overflow-x-auto bg-white shadow-md rounded-lg mt-4">
                    <table className="w-full border-collapse">
                        <thead>
                        <tr className="bg-gray-800 text-white">
                            <th className="p-3 text-left">Číslo smlouvy</th>
                            <th className="p-3 text-left">Typ</th>
                            <th className="p-3 text-left">Částka (Kč)</th>
                            <th className="p-3 text-left">Platnost</th>
                            <th className="p-3 text-center">Akce</th>
                        </tr>
                        </thead>
                        <tbody>
                        {insurances.length > 0 ? (
                            insurances.map(insurance => (
                                <tr key={insurance.id} className="border-b hover:bg-gray-100 transition">
                                    <td className="p-3">{insurance.policyNumber}</td>
                                    <td className="p-3">{insurance.type}</td>
                                    <td className="p-3">{insurance.amount}</td>
                                    <td className="p-3">{insurance.validFrom} - {insurance.validTo}</td>
                                    <td className="p-3 flex justify-center gap-2">
                                        <Link to={`/insured-persons/${id}/edit-insurance/${insurance.id}`}
                                              className="flex items-center gap-1 px-3 py-1 bg-yellow-500 text-white rounded-md hover:bg-yellow-600 transition">
                                            <Pencil size={18} /> Editovat
                                        </Link>
                                        <button
                                            className="flex items-center gap-1 px-3 py-1 bg-red-600 text-white rounded-md hover:bg-red-700 transition"
                                            onClick={() => removeInsurance(insurance.id)}>
                                            <Trash size={18} /> Smazat
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="5" className="p-4 text-center text-gray-500">Žádné pojištění</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            )}

            {/* Akční tlačítka */}
            <div className="mt-6 flex justify-center gap-4">
                <Link to={`/insured-persons/${id}/assign-insurance`}
                      className="flex items-center gap-2 bg-green-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-green-700 transition">
                    <PlusCircle size={20} /> Přidat pojištění
                </Link>
                <button
                    className="flex items-center gap-2 bg-gray-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-gray-700 transition"
                    onClick={() => navigate("/insured-persons")}>
                    <ArrowLeft size={20} /> Zpět
                </button>
            </div>
        </div>
    );
};

export default InsuredPersonDetail;
