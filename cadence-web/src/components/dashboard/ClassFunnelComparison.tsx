import { useEffect, useState } from 'react'
import {
    getClassFunnel,
    type ClassFunnel,
} from '../../services/analyticsService'
import { getUpcomingClasses } from '../../services/classService'
import type { ClassSession } from '../../types/ClassSession'

function ClassFunnelComparison() {
    const [classes, setClasses] = useState<ClassSession[]>([])
    const [funnels, setFunnels] = useState<ClassFunnel[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        async function loadClassFunnels() {
            try {
                const classData = await getUpcomingClasses()
                setClasses(classData)

                const funnelData = await Promise.all(
                    classData.map((classSession) =>
                        getClassFunnel(classSession.id)
                    )
                )

                setFunnels(funnelData)
            } catch {
                setError('Unable to load class analytics.')
            } finally {
                setLoading(false)
            }
        }

        loadClassFunnels()
    }, [])

    if (loading) return <p>Loading class comparisons...</p>
    if (error) return <p className="text-red-600">{error}</p>

    return (
        <section className="mt-8 rounded-xl border border-slate-200 bg-white p-6">
            <h3 className="mb-5 text-xl font-bold text-slate-900">
                Class Conversion Comparison
            </h3>

            <div className="overflow-x-auto">
                <table className="w-full text-left text-sm">
                    <thead className="border-b text-slate-500">
                    <tr>
                        <th className="py-3">Class</th>
                        <th className="py-3">Views</th>
                        <th className="py-3">Starts</th>
                        <th className="py-3">Completed</th>
                        <th className="py-3">Conversion</th>
                    </tr>
                    </thead>

                    <tbody>
                    {classes.map((classSession) => {
                        const funnel = funnels.find(
                            (item) =>
                                item.classSessionId === classSession.id
                        )

                        if (!funnel) return null

                        return (
                            <tr
                                key={classSession.id}
                                className="border-b border-slate-100"
                            >
                                <td className="py-4 font-medium">
                                    {classSession.name}
                                </td>
                                <td className="py-4">
                                    {funnel.classViews}
                                </td>
                                <td className="py-4">
                                    {funnel.bookingStarts}
                                </td>
                                <td className="py-4">
                                    {funnel.bookingCompletions}
                                </td>
                                <td className="py-4 font-semibold">
                                    {funnel.overallConversionRate.toFixed(1)}%
                                </td>
                            </tr>
                        )
                    })}
                    </tbody>
                </table>
            </div>
        </section>
    )
}

export default ClassFunnelComparison