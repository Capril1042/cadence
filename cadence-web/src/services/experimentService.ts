export interface ExperimentAssignment {
    id: number
    variant: string
    experiment: {
        id: number
        name: string
        status: string
    }
    member: {
        id: number
    }
}

export async function getExperimentAssignment(
    experimentId: number,
    memberId: number
): Promise<ExperimentAssignment> {
    const response = await fetch(
        `http://localhost:8080/api/experiments/${experimentId}/assign?memberId=${memberId}`,
        { method: 'POST' }
    )

    if (!response.ok) {
        throw new Error('Failed to get experiment assignment')
    }

    return response.json()
}