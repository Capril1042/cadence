import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Dashboard from './pages/Dashboard'
import Members from './pages/Members'

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Dashboard />} />
                <Route path="/members" element={<Members />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App