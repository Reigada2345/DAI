"use client"

import { useState, useCallback } from "react"
import { Upload, File, CheckCircle, AlertCircle } from 'lucide-react'

interface ParsedData {
  stops: any[]
  errors: string[]
}

export default function FileUpload() {
  const [files, setFiles] = useState<File[]>([])
  const [uploading, setUploading] = useState(false)
  const [results, setResults] = useState<ParsedData | null>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFiles(Array.from(e.target.files))
      setResults(null)
    }
  }

  const parseStopsFile = (content: string): any[] => {
    const lines = content.split("\n").filter((line) => line.trim())
    const stops = []

    for (let i = 0; i < lines.length; i++) {
      const line = lines[i].trim()
      if (!line || line.includes("stop_id")) continue

      // Parse formato: stop_id,"stop_name",stop_lat,stop_lon,zone_id
      const matches = line.match(/^(\d+),"([^"]+)",([^,]+),([^,]+),(\d+)$/)
      if (matches) {
        stops.push({
          stop_id: parseInt(matches[1]),
          stop_name: matches[2],
          stop_lat: parseFloat(matches[3]),
          stop_lon: parseFloat(matches[4]),
          zone_id: parseInt(matches[5]),
        })
      } else {
        // Tentar formato alternativo sem aspas
        const parts = line.split(",")
        if (parts.length >= 5) {
          const stopId = parseInt(parts[0])
          const stopName = parts[1].replace(/"/g, "")
          const stopLat = parseFloat(parts[2])
          const stopLon = parseFloat(parts[3])
          const zoneId = parseInt(parts[4])
          
          if (!isNaN(stopId) && !isNaN(stopLat) && !isNaN(stopLon) && !isNaN(zoneId)) {
            stops.push({
              stop_id: stopId,
              stop_name: stopName,
              stop_lat: stopLat,
              stop_lon: stopLon,
              zone_id: zoneId,
            })
          }
        }
      }
    }

    return stops
  }

  const processFiles = async () => {
    setUploading(true)
    const parsedData: ParsedData = { stops: [], errors: [] }

    try {
      for (const file of files) {
        const content = await file.text()
        
        if (file.name.toLowerCase().includes("stop") || file.name.endsWith(".txt")) {
          try {
            const stops = parseStopsFile(content)
            parsedData.stops.push(...stops)
          } catch (error) {
            parsedData.errors.push(`Erro ao processar ${file.name}`)
          }
        }
      }

      // Guardar no localStorage para a aba Paragens
      if (parsedData.stops.length > 0) {
        localStorage.setItem('importedStops', JSON.stringify(parsedData.stops))
      }

      setResults(parsedData)
    } catch (error) {
      parsedData.errors.push("Erro geral")
    } finally {
      setUploading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center">
        <Upload className="mx-auto h-12 w-12 text-gray-400 mb-4" />
        <div>
          <p className="text-lg text-gray-600 mb-2">Selecione os ficheiros do projeto DAI</p>
          <input
            type="file"
            multiple
            accept=".txt,.csv,.sql"
            onChange={handleFileChange}
            className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
          />
        </div>
      </div>

      {files.length > 0 && (
        <div className="space-y-4">
          <h3 className="text-lg font-semibold">Ficheiros Selecionados:</h3>
          <div className="grid gap-2">
            {files.map((file, index) => (
              <div key={index} className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg">
                <File className="h-5 w-5 text-gray-500" />
                <span className="flex-1">{file.name}</span>
                <span className="text-sm text-gray-500">{(file.size / 1024).toFixed(1)} KB</span>
              </div>
            ))}
          </div>

          <button
            onClick={processFiles}
            disabled={uploading}
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700 disabled:opacity-50"
          >
            {uploading ? "A processar..." : "Processar Ficheiros"}
          </button>
        </div>
      )}

      {results && (
        <div className="space-y-4">
          <h3 className="text-lg font-semibold">Resultados:</h3>

          {results.stops.length > 0 && (
            <div className="bg-green-50 border border-green-200 rounded-lg p-4">
              <div className="flex items-center">
                <CheckCircle className="h-4 w-4 text-green-600 mr-2" />
                <span className="text-green-800">{results.stops.length} paragens importadas com sucesso</span>
              </div>
            </div>
          )}

          {results.errors.length > 0 && (
            <div className="bg-red-50 border border-red-200 rounded-lg p-4">
              <div className="flex items-center">
                <AlertCircle className="h-4 w-4 text-red-600 mr-2" />
                <div>
                  <p className="text-red-800">Erros encontrados:</p>
                  <ul className="list-disc list-inside mt-2">
                    {results.errors.map((error, index) => (
                      <li key={index} className="text-sm text-red-700">{error}</li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  )
}