import Header from '../components/layout/Header'
import UpcomingClasses from '../components/dashboard/UpcomingClasses'

function Classes() {
    return (
        <div className="min-h-screen bg-slate-50">
            <Header />

            <main className="mx-auto max-w-7xl px-8 py-10">
                <h2 className="text-3xl font-bold text-slate-900">
                    Classes
                </h2>
                <p className="mt-2 text-slate-500">
                    Browse classes and manage bookings.
                </p>

                <div className="mt-8">
                    <UpcomingClasses />
                </div>
            </main>
        </div>
    )
}

export default Classes