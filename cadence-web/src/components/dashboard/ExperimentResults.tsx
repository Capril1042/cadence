
import { useEffect, useState } from 'react'
import {
    getExperimentResults,
    type ExperimentResult,
} from '../../services/analyticsService'

export default function ExperimentResults() {
    const [results, setResults] = useState<ExperimentResult[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        async function loadResults() {
            try {
                const data = await getExperimentResults(1)
                setResults(data)
            } catch (error) {
                setError('Unable to load experiment results')
                console.error(error)
            } finally {
                setLoading(false)
            }
        }

        loadResults()
    }, [])

    if (loading) return <p>Loading experiment results...</p>
    if (error) return <p>{error}</p>

    const variants = ['A', 'B']
    const getVariant = (variant: string) =>
        results.find((result) => result.variant === variant)

    const totalExposures = results.reduce(
        (total, result) => total + result.exposedMembers,
        0
    )

    return (
        <section className="mt-8">
            <div className="mb-5 flex items-center justify-between">
                <div>
                    <h2 className="text-xl font-bold text-slate-900">
                        Experiment Results
                    </h2>
                    <p className="mt-1 text-sm text-slate-500">
                        Booking Button Test
                    </p>
                </div>
                <span className="rounded-full bg-green-100 px-3 py-1 text-xs font-semibold text-green-700">
                    Running
                </span>
            </div>

            <div className="mb-5 rounded-xl border border-slate-200 bg-slate-50 p-5">
                <p className="font-semibold text-slate-900">
                    Sample size matters
                </p>
                <p className="mt-1 text-sm text-slate-600">
                    Results are directional until each variant has enough
                    participants. Small samples are not sufficient to declare
                    a winning variation.
                </p>
                <p className="mt-3 text-sm font-semibold text-slate-700">
                    Total exposed members: {totalExposures}
                </p>
            </div>

            <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
                {variants.map((variant) => {
                    const result = getVariant(variant)

                    return (
                        <div
                            key={variant}
                            className="rounded-xl bg-white p-6 shadow-sm"
                        >
                            <div className="mb-5 flex items-center justify-between">
                                <h3 className="text-lg font-semibold text-slate-900">
                                    Variant {variant}
                                </h3>
                                <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-medium text-slate-600">
                                    {variant === 'A' ? 'Control' : 'Test'}
                                </span>
                            </div>

                            {result ? (
                                <>
                                    <div className="mb-5 rounded-lg bg-slate-50 p-4">
                                        <p className="text-sm text-slate-500">
                                            Start conversion
                                        </p>
                                        <p className="mt-1 text-3xl font-bold text-slate-900">
                                            {result.startConversionRate.toFixed(1)}%
                                        </p>
                                    </div>

                                    <div className="grid grid-cols-2 gap-5">
                                        <div>
                                            <p className="text-sm text-slate-500">
                                                Exposed members
                                            </p>
                                            <p className="mt-1 text-xl font-semibold text-slate-900">
                                                {result.exposedMembers}
                                            </p>
                                        </div>

                                        <div>
                                            <p className="text-sm text-slate-500">
                                                Booking starts
                                            </p>
                                            <p className="mt-1 text-xl font-semibold text-slate-900">
                                                {result.bookingStarts}
                                            </p>
                                        </div>

                                        <div>
                                            <p className="text-sm text-slate-500">
                                                Completed bookings
                                            </p>
                                            <p className="mt-1 text-xl font-semibold text-slate-900">
                                                {result.bookingsCompleted}
                                            </p>
                                        </div>

                                        <div>
                                            <p className="text-sm text-slate-500">
                                                Completion conversion
                                            </p>
                                            <p className="mt-1 text-xl font-semibold text-slate-900">
                                                {result.completionConversionRate.toFixed(1)}%
                                            </p>
                                        </div>
                                    </div>
                                </>
                            ) : (
                                <p className="text-sm text-slate-500">
                                    No exposure data yet
                                </p>
                            )}
                        </div>
                    )
                })}
            </div>
        </section>
    )
}
