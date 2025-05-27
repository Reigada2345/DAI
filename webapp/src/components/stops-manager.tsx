"use client"

import { useState, useEffect } from "react"
import { Search, Edit, Trash2, MapPin } from 'lucide-react'

interface Stop {
  stop_id: number
  stop_name: string
  stop_lat: number
  stop_lon: number
  zone_id: number
}

export default function StopsManager() {
  const [stops, setStops] = useState<Stop[]>([])
  const [searchTerm, setSearchTerm] = useState("")

  // Carregar dados do localStorage (onde foram guardados após importação)
  useEffect(() => {
    const savedStops = localStorage.getItem('importedStops')
    if (savedStops) {
      try {
        const parsedStops = JSON.parse(savedStops)
        setStops(parsedStops)
      } catch (error) {
        console.error('Erro ao carregar paragens:', error)
        // Dados de exemplo se não conseguir carregar
        setStops([
          { stop_id: 1, stop_name: "BOM JESUS", stop_lat: 41.554462, stop_lon: -8.381193, zone_id: 1 },
          { stop_id: 2, stop_name: "REPÚBLICA II", stop_lat: 41.553543, stop_lon: -8.383646, zone_id: 1 },
        ])
      }
    }
  }, [])

  const filteredStops = stops.filter(
    (stop) =>
      stop.stop_name.toLowerCase().includes(searchTerm.toLowerCase()) || 
      stop.stop_id.toString().includes(searchTerm)
  )

  const handleDelete = (stopId: number) => {
    const updatedStops = stops.filter((stop) => stop.stop_id !== stopId)
    setStops(updatedStops)
    localStorage.setItem('importedStops', JSON.stringify(updatedStops))
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div className="relative flex-1 max-w-sm">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
          <input
            placeholder="Pesquisar paragens..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-10 w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center gap-2">
          <MapPin className="h-4 w-4" />
          Nova Paragem
        </button>
      </div>

      <div className="bg-blue-50 p-4 rounded-lg">
        <p className="text-blue-800 font-semibold">
          📊 Total: {stops.length} paragens | 🔍 Filtradas: {filteredStops.length}
        </p>
      </div>

      <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nome</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Latitude</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Longitude</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Zona</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Ações</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {filteredStops.slice(0, 50).map((stop) => (
              <tr key={stop.stop_id} className="hover:bg-gray-50">
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                  {stop.stop_id}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {stop.stop_name}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {stop.stop_lat.toFixed(6)}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {stop.stop_lon.toFixed(6)}
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                    Zona {stop.zone_id}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <div className="flex gap-2">
                    <button className="text-blue-600 hover:text-blue-900 p-1 rounded">
                      <Edit className="h-4 w-4" />
                    </button>
                    <button 
                      onClick={() => handleDelete(stop.stop_id)}
                      className="text-red-600 hover:text-red-900 p-1 rounded"
                    >
                      <Trash2 className="h-4 w-4" />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filteredStops.length > 50 && (
        <div className="text-center text-gray-500">
          A mostrar primeiras 50 de {filteredStops.length} paragens. Use a pesquisa para filtrar.
        </div>
      )}
    </div>
  )
}