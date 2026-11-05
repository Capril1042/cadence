
import { useEffect, useState } from 'react'
import {
    getBookingFunnel,
    type BookingFunnel as BookingFunnelData,
} from '../../services/analyticsService'

function BookingFunnel() {
    const [funnel, setFunnel] = useState<BookingFunnelData | null>(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        async function loadFunnel() {
            try {
                const data = await getBookingFunnel()
                setFunnel(data)
            } catch {
                setError('Unable to load booking analytics.')
            } finally {
                setLoading(false)
            }
        }

        loadFunnel()
    }, [])

    if (loading) return <p>Loading booking analytics...</p>
    if (error) return <p className="text-red-600">{error}</p>
    if (!funnel) return null

    const stages = [
        {
            label: 'Class Views',
            count: funnel.classViews,
            rate: 100,
            description: 'People who viewed classes',
        },
        {
            label: 'Booking Starts',
            count: funnel.bookingStarts,
            rate: funnel.viewToStartRate,
            description: 'Views that became booking attempts',
        },
        {
            label: 'Bookings Completed',
            count: funnel.bookingCompletions,
            rate: funnel.overallConversionRate,
            description: 'Views that became completed bookings',
        },
    ]

    return (
        <section className="rounded-xl border border-slate-200 bg-white p-6">
            <h3 className="mb-2 text-xl font-bold text-slate-900">
                Booking Conversion Funnel
            </h3>
            <p className="mb-6 text-sm text-slate-500">
                Track how class interest turns into completed bookings.
            </p>

            <div className="space-y-6">
                {stages.map((stage) => (
                    <div key={stage.label}>
                        <div className="mb-2 flex items-center justify-between">
                            <div>
                                <p className="font-semibold text-slate-900">
                                    {stage.label}
                                </p>
                                <p className="text-xs text-slate-500">
                                    {stage.description}
                                </p>
                            </div>
                            <div className="text-right">
                                <p className="text-xl font-bold text-slate-900">
                                    {stage.count}
                                </p>
                                <p className="text-xs text-slate-500">
                                    {stage.rate.toFixed(1)}% of views
                                </p>
                            </div>
                        </div>

                        <div
                            className="h-3 overflow-hidden rounded-full bg-slate-100"
                            role="progressbar"
                            aria-label={`${stage.label} as a percentage of class views`}
                            aria-valuenow={stage.rate}
                            aria-valuemin={0}
                            aria-valuemax={100}
                        >
                            <div
                                className="h-full rounded-full bg-indigo-600 transition-all duration-500"
                                style={{
                                    width: `${Math.min(100, Math.max(0, stage.rate))}%`,
                                }}
                            />
                        </div>
                    </div>
                ))}
            </div>

            <div className="mt-6 border-t border-slate-100 pt-4">
                <p className="text-sm text-slate-500">
                    View-to-booking conversion
                </p>
                <p className="text-3xl font-bold text-indigo-600">
                    {funnel.overallConversionRate.toFixed(1)}%
                </p>
                <p className="mt-1 text-xs text-slate-500">
                    {funnel.startToCompleteRate.toFixed(1)}% of booking starts
                    resulted in a completed booking.
                </p>
            </div>
        </section>
    )
}

export default BookingFunnel
