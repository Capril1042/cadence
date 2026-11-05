
import { useEffect, useState } from 'react'
import {
    getClassAnalyticsSummary,
    type ClassAnalyticsSummary,
} from '../../services/analyticsService'

function ClassFunnelComparison() {
    const [analytics, setAnalytics] = useState<ClassAnalyticsSummary[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        async function loadClassAnalytics() {
            try {
                const data = await getClassAnalyticsSummary()
                setAnalytics(data)
            } catch {
                setError('Unable to load class analytics.')
            } finally {
                setLoading(false)
            }
        }

        loadClassAnalytics()
    }, [])

    if (loading) return <p>Loading class comparisons...</p>
    if (error) return <p className="text-red-600">{error}</p>

    return (
        <section className="mt-8 rounded-xl border border-slate-200 bg-white p-6">
            <h3 className="mb-5 text-xl font-bold text-slate-900">
                Class Conversion Comparison
            </h3>
            <p className="mb-4 text-sm text-slate-500">
                Conversion rates are based on recorded class views.
                Small sample sizes should be interpreted with caution.
            </p>
            <div className="overflow-x-auto">
                <table className="w-full text-left text-sm">
                    <thead className="border-b text-slate-500">
                    <tr>
                        <th className="py-3">Class</th>
                        <th className="py-3">Views (Sample Size)</th>                        <th className="py-3">Starts</th>
                        <th className="py-3">Completed</th>
                        <th className="py-3">Conversion</th>
                        <th className="py-3">Abandonment</th>
                    </tr>
                    </thead>

                    <tbody>
                    {analytics.map((item) => (
                        <tr
                            key={item.classSessionId}
                            className="border-b border-slate-100"
                        >
                            <td className="py-4 font-medium">
                                {item.className}
                            </td>
                            <td className="py-4">{item.views}</td>
                            <td className="py-4">{item.bookingStarts}</td>
                            <td className="py-4">{item.bookingsCompleted}</td>
                            <td className="py-4 font-semibold">
                                {item.viewToBookingRate.toFixed(1)}%
                            </td>
                            <td className="py-4">
                                {item.bookingAbandonmentRate === null
                                    ? 'N/A'
                                    : `${item.bookingAbandonmentRate.toFixed(1)}%`}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </section>
    )
}

export default ClassFunnelComparison
