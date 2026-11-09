import Header from '../components/layout/Header'
import MetricCard from '../components/dashboard/MetricCard'
import UpcomingClasses from '../components/dashboard/UpcomingClasses'
import BookingFunnel from '../components/dashboard/BookingFunnel'
import ClassFunnelComparison from '../components/dashboard/ClassFunnelComparison'
import ExperimentResults from '../components/dashboard/ExperimentResults'
import RetentionOverview from '../components/dashboard/RetentionOverview'

function Dashboard() {
  return (
    <div className="min-h-screen bg-slate-50">
      <Header />

      <main className="mx-auto max-w-7xl px-8 py-10">
        <div>
          <h2 className="text-3xl font-bold text-slate-900">
            Good morning!
          </h2>
          <p className="mt-2 text-slate-500">
            Here's what's happening at your studio.
          </p>
        </div>

        <div className="mt-8 grid grid-cols-1 gap-6 md:grid-cols-3">
          <MetricCard title="Total Members" value="248" />
          <MetricCard title="Monthly Revenue" value="$12,450" />
          <MetricCard title="Class Bookings" value="186" />
        </div>
        <BookingFunnel />
        <ClassFunnelComparison />
        <ExperimentResults />
        <RetentionOverview />
        <UpcomingClasses />
      </main>
    </div>
  )
}

export default Dashboard