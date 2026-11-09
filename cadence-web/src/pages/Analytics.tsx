import Header from '../components/layout/Header'
import BookingFunnel from '../components/dashboard/BookingFunnel'
import ClassFunnelComparison from '../components/dashboard/ClassFunnelComparison'
import ExperimentResults from '../components/dashboard/ExperimentResults'
import RetentionOverview from '../components/dashboard/RetentionOverview'

function Analytics() {
    return (
        <div className="min-h-screen bg-slate-50">
            <Header />

            <main className="mx-auto max-w-7xl px-8 py-10">
                <h2 className="text-3xl font-bold text-slate-900">
                    Analytics
                </h2>
                <p className="mt-2 text-slate-500">
                    Understand bookings, experimentation, and member retention.
                </p>

                <section className="mt-10">
                    <h3 className="mb-6 text-xl font-semibold text-slate-900">
                        Booking Performance
                    </h3>
                    <div className="space-y-8">
                        <BookingFunnel />
                        <ClassFunnelComparison />
                    </div>
                </section>

                <section className="mt-12">
                    <h3 className="mb-6 text-xl font-semibold text-slate-900">
                        Growth & Retention
                    </h3>
                    <div className="space-y-8">
                        <ExperimentResults />
                        <RetentionOverview />
                    </div>
                </section>
            </main>
        </div>
    )
}

export default Analytics