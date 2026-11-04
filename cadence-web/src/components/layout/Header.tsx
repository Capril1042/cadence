import { Link } from 'react-router-dom'

function Header() {
    return (
        <header className="flex items-center justify-between bg-white px-8 py-5 shadow-sm">
            <Link to="/" className="text-2xl font-bold text-indigo-600">
                cadence
            </Link>

            <nav className="flex gap-6 text-sm font-medium text-gray-600">
                <Link to="/" className="hover:text-indigo-600">
                    Dashboard
                </Link>

                <Link to="/members" className="hover:text-indigo-600">
                    Members
                </Link>

                <a href="#" className="hover:text-indigo-600">
                    Classes
                </a>

                <a href="#" className="hover:text-indigo-600">
                    Analytics
                </a>
            </nav>
        </header>
    )
}

export default Header